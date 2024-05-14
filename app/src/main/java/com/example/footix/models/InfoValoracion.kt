package com.example.footix.models

import com.squareup.moshi.Json

data class InfoValoracion(
    @field:Json(name = "partido")
    val partido:Int,
    @field:Json(name = "valoracion")
    val valoracion:Int
)
