package com.jkapps.givemeagif.data

import com.jkapps.givemeagif.data.api.GifService
import com.jkapps.givemeagif.domain.GifRepository
import com.jkapps.givemeagif.domain.entity.Gif

class GifRepositoryImpl(private val service: GifService) : GifRepository {

    override suspend fun getRandomGif(): Gif = service.getRandomGif()

}