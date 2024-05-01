package com.example.footix.models

import com.squareup.moshi.Json

data class RegisterInfo(
@field:Json(name = "nombre")
val nombre: String,
@field:Json(name = "correo")
val correo: String,
@field:Json(name = "contrasenia")
val contrasenia: String,
@field:Json(name = "descripcion")
val descripcion: String,
)
