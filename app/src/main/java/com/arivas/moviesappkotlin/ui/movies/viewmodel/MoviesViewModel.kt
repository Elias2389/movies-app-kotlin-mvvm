package com.arivas.moviesappkotlin.ui.movies.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.arivas.moviesappkotlin.BuildConfig
import com.arivas.moviesappkotlin.common.dto.MoviesResponse
import com.arivas.moviesappkotlin.common.network.RetrofitService
import com.arivas.moviesappkotlin.common.network.services.MoviesServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesViewModel(private val service: RetrofitService): ViewModel() {
    private var moviesList: MutableLiveData<MoviesResponse>? = null

    fun fetchData(): MutableLiveData<MoviesResponse> {
        if (moviesList == null) {
            moviesList = MutableLiveData<MoviesResponse>()
            loadMovies()
        }
        return moviesList as MutableLiveData<MoviesResponse>
    }

    @SuppressLint("CheckResult")
    private fun loadMovies() {
        getCall()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                moviesList?.value = response
            },{ error ->
                Log.e("Error,", error.message)
            })
    }

    private fun getService(): MoviesServices {
        return service.client().create(MoviesServices::class.java)
    }

    private fun getCall(): Observable<MoviesResponse> {
        return getService().getPopularMovies(BuildConfig.API_KEY)
    }
}