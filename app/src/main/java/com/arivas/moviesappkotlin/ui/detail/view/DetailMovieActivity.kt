package com.arivas.moviesappkotlin.ui.detail.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.arivas.moviesappkotlin.BuildConfig
import com.arivas.moviesappkotlin.R
import com.arivas.moviesappkotlin.common.Constants.Companion.MOVIE_ID
import com.arivas.moviesappkotlin.common.dto.DetailMovie
import com.arivas.moviesappkotlin.common.network.RetrofitService
import com.arivas.moviesappkotlin.ui.detail.viewmodel.DetailMovieViewModel
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.koin.android.ext.android.inject

class DetailMovieActivity : AppCompatActivity() {
    private var overview: TextView? = null
    private var imgDetail: SimpleDraweeView? = null
    private var model: DetailMovieViewModel? = null
    private val service: RetrofitService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        overview = findViewById(R.id.overview)
        imgDetail = findViewById(R.id.image_detail)

        model = DetailMovieViewModel(service)

        detailMovie()
    }

    private fun detailMovie() {
        val movieId = intent.getIntExtra(MOVIE_ID,0)
        model?.fetchDetail(movieId)?.observe(this, Observer {
            successDetailMovie(it!!)
        })
    }

    private fun successDetailMovie(detailMovie: DetailMovie) {
        toolbar_detail.title = detailMovie.title
        overview?.text = detailMovie.overview
        imgDetail?.setImageURI(BuildConfig.BASE_URL_IMAGES + detailMovie.backdropPath)
    }

    fun error() {

    }
}
