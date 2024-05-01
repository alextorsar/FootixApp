package com.example.footix.models

import com.squareup.moshi.Json

data class Estadisticas(
    @field:Json(name = "numeroPartidos")
    val numeroPartidos: Int,
    @field:Json(name = "numeroSegundos")
    val numeroSegundos: Int,
    @field:Json(name = "equipoMasVisto")
    val equipoMasVisto: Int,
    @field:Json(name = "vecesVisto")
    val vecesVisto: Int,
)