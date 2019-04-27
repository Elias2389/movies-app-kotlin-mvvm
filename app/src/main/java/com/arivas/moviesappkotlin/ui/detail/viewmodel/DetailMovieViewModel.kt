package com.arivas.moviesappkotlin.ui.detail.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.arivas.moviesappkotlin.BuildConfig
import com.arivas.moviesappkotlin.common.dto.DetailMovie
import com.arivas.moviesappkotlin.common.network.RetrofitService
import com.arivas.moviesappkotlin.common.network.services.MoviesServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailMovieViewModel( private val service: RetrofitService): ViewModel() {
    private var detailMovie: MutableLiveData<DetailMovie>? = null

    fun fetchDetail(movieId: Int): MutableLiveData<DetailMovie> {
        if (detailMovie == null) {
            detailMovie = MutableLiveData<DetailMovie>()

            loadDetail(movieId)
        }
        return detailMovie as MutableLiveData<DetailMovie>
    }

    @SuppressLint("CheckResult")
    private fun loadDetail(movieId: Int) {
        getCall(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                detailMovie?.value = response
            },{ error ->
                Log.e("Error,", error.message)
            })
    }

    private fun getService(): MoviesServices {
        return service.client().create(MoviesServices::class.java)
    }

    private fun getCall(movieId: Int): Observable<DetailMovie> {
        return getService().getDetailMovie(movieId, BuildConfig.API_KEY)
    }
}