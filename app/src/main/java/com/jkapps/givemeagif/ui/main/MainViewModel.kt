package com.jkapps.givemeagif.ui.main

import androidx.lifecycle.*
import com.jkapps.givemeagif.domain.Event
import com.jkapps.givemeagif.domain.GifRepository
import com.jkapps.givemeagif.domain.entity.Gif
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class MainViewModel(private val repository: GifRepository) : ViewModel() {
    private val _gif: MutableLiveData<Gif> = MutableLiveData()
    private val _isErrorDisplaying: MutableLiveData<Boolean> = MutableLiveData()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val _isButtonEnable: MutableLiveData<Boolean> = MutableLiveData(false)

    val gif: LiveData<Gif> get() = _gif
    val error: LiveData<Boolean> get() = _isErrorDisplaying
    val isLoading: LiveData<Boolean> get() = _isLoading
    val isButtonEnable: LiveData<Boolean> get() = _isButtonEnable

    private val previousGifs: Stack<Gif> = Stack()

    init {
        getNewGif()
    }

    fun getNewGif() {
        if (isLoading.value == true) return

        _gif.value?.let { previousGifs.add(it) }
        _isLoading.value = true
        viewModelScope.launch {
            try {
                var gif = repository.getRandomGif()
                while (gif.gifUrl == null) {
                    gif = repository.getRandomGif()
                }
                _gif.value = gif
                _isLoading.value = false
                _isErrorDisplaying.value = false

                if (previousGifs.isNotEmpty()) _isButtonEnable.value = true
            } catch (e: Exception) {
                if (error.value == null || error.value == false) _isErrorDisplaying.value = true
                _isLoading.value = false
            }
        }
    }

    fun getPrevious() {
        _gif.value = previousGifs.pop()
        if (previousGifs.isEmpty()) _isButtonEnable.value = false
    }
}