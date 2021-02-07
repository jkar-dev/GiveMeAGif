package com.jkapps.givemeagif.data.api

import com.jkapps.givemeagif.domain.entity.Gif
import retrofit2.http.GET


interface GifService {

    @GET("/random")
    suspend fun getRandomGif() : Gif
}