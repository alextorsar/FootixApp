package com.example.footix.models

import com.squareup.moshi.Json

data class InfoPartido(
    @field:Json(name = "partido")
    val partido:Int
)
