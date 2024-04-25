package com.example.footix.models

import com.squareup.moshi.Json
import java.net.IDN

data class User(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "idBd")
    val idBd: Int,
    @field:Json(name = "nombre")
    val nombre: String,
    @field:Json(name = "correo")
    val correo: String,
    @field:Json(name = "fotoPerfil")
    val fotoPerfil: String,
    @field:Json(name = "descripcion")
    val descripcion: String,
    @field:Json(name = "equipoFavorito")
    val equipoFavorito: Int
)
