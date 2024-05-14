package com.example.footix.models

import com.squareup.moshi.Json

data class InfoResenia(
    @field:Json(name = "partido")
    val partido:Int,
    @field:Json(name = "comentario")
    val comentario:String
)
