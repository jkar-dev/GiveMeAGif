package com.jkapps.givemeagif.di

import com.jkapps.givemeagif.data.GifRepositoryImpl
import com.jkapps.givemeagif.data.api.NetworkModule

class AppContainer {
    val repository = GifRepositoryImpl(NetworkModule.gifService)
}