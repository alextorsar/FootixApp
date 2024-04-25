package com.example.footix.models

import com.squareup.moshi.Json
import java.net.IDN

data class Equipo(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "idBd")
    val idBd: Int,
    @field:Json(name = "nombre")
    val nombre: String,
    @field:Json(name = "fotoEscudo")
    val fotoEscudo: String
)