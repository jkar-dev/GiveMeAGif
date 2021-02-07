package com.jkapps.givemeagif.ui.main

import androidx.lifecycle.*
import com.jkapps.givemeagif.domain.GifRepository
import com.jkapps.givemeagif.domain.entity.Gif
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val repository: GifRepository) : ViewModel() {
    private val _gif : MutableLiveData<Gif> = MutableLiveData()
    private val _isButtonEnable : MutableLiveData<Boolean> = MutableLiveData(false)

    val gif : LiveData<Gif> get() = _gif
    val isButtonEnable : LiveData<Boolean> get() = _isButtonEnable

    private val previousGifs : Stack<Gif> = Stack()

    init {
        getNewGif()
    }

    fun getNewGif() {
        _gif.value?.let { previousGifs.add(it) }
        viewModelScope.launch {
            var gif = repository.getRandomGif()
            while (gif.gifUrl == null) {
                gif = repository.getRandomGif()
            }
            _gif.value = gif

            if (previousGifs.isNotEmpty()) _isButtonEnable.value = true
        }
    }

    fun getPrevious() {
        _gif.value = previousGifs.pop()
        if (previousGifs.isEmpty()) _isButtonEnable.value = false
    }

}