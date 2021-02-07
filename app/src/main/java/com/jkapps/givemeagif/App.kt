package com.jkapps.givemeagif

import android.app.Application
import com.jkapps.givemeagif.di.AppContainer

class App : Application() {
    val appContainer = AppContainer()
}