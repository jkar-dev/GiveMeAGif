package com.jkapps.givemeagif.domain

import com.jkapps.givemeagif.domain.entity.Gif

interface GifRepository {
    suspend fun getRandomGif() : Gif
}