package com.arivas.moviesappkotlin.di

import com.arivas.moviesappkotlin.common.network.RetrofitService
import com.arivas.moviesappkotlin.ui.detail.viewmodel.DetailMovieViewModel
import com.arivas.moviesappkotlin.ui.movies.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitService() }
    viewModel { MoviesViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
}
