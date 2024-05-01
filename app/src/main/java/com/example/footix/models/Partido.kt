package com.example.footix.models

import com.squareup.moshi.Json
import java.util.Date

data class Partido(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "idBd")
    val idBd: Int,
    @field:Json(name = "estadio")
    val estadio: String,
    @field:Json(name = "status")
    var status: Int,
    @field:Json(name = "jornada")
    val jornada: Int,
    @field:Json(name = "fecha")
    var fecha: String = "",
    @field:Json(name = "equipoLocal")
    var equipoLocal: Int,
    @field:Json(name = "equipoVisitante")
    var equipoVisitante: Int,
    @field:Json(name = "golesLocal")
    var golesLocal: Int,
    @field:Json(name = "golesVisitante")
    var golesVisitante: Int
)
