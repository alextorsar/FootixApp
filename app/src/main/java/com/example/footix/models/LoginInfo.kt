package com.example.footix.models

import com.squareup.moshi.Json

data class LoginInfo(
    @field:Json(name = "correo")
    var correo: String = "",
    @field:Json(name = "contrasenia")
    var contrasenia: String = ""
)
