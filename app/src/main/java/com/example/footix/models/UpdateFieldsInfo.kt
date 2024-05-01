package com.example.footix.models

import com.squareup.moshi.Json

data class UpdateFieldsInfo(
    @field:Json(name = "nombre")
    val nombre: String,
    @field:Json(name = "correo")
    val correo: String,
    @field:Json(name = "descripcion")
    val descripcion: String = "",
    @field:Json(name = "equipoFavorito")
    var equipoFavorito: Int = -1
)
