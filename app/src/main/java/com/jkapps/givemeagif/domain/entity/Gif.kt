package com.jkapps.givemeagif.domain.entity

import com.squareup.moshi.Json

data class Gif(
    @field:Json(name = "id")
    val id : Int,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "gifURL")
    val gifUrl : String)