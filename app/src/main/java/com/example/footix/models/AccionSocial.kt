package com.example.footix.models

import com.squareup.moshi.Json

data class AccionSocial(
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
    @field:Json(name = "valoracion")
    val valoracion: Int = -1,
    @field:Json(name = "comentario")
    val comentario: String = "",
    @field:Json(name = "nombrePartido")
    val nombrePartido: String
)
