package com.jkapps.givemeagif.di

import com.jkapps.givemeagif.data.GifRepositoryImpl

class AppContainer {
    val repository = GifRepositoryImpl(NetworkModule.gifService)
}