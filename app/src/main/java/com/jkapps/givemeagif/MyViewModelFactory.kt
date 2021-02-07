package com.jkapps.givemeagif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jkapps.givemeagif.domain.GifRepository
import com.jkapps.givemeagif.ui.main.MainViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val repository: GifRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) return MainViewModel(repository) as T
        else throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }

}