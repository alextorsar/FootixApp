package com.example.footix.models

import com.squareup.moshi.Json

data class Seguido(
    @field:Json(name = "idBd")
    val idBd: Int,
    @field:Json(name = "seguidor")
    val seguidor: Int,
    @field:Json(name = "seguido")
    var seguido: Int,
)
