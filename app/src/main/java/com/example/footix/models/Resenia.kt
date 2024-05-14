package com.example.footix.models

import com.squareup.moshi.Json

data class Resenia(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "idBd")
    val idBd: Int,
    @field:Json(name = "usuario")
    val usuario: Int,
    @field:Json(name = "partido")
    val partido: Int,
    @field:Json(name = "hora")
    val hora: String,
    @field:Json(name = "comentario")
    val comentario: String = "",
)
