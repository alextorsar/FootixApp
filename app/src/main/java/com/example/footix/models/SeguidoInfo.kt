package com.example.footix.models

import com.squareup.moshi.Json

data class SeguidoInfo(
    @field:Json(name = "seguido")
    val seguido:Int
)
