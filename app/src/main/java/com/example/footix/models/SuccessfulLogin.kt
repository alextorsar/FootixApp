package com.example.footix.models

import com.squareup.moshi.Json

data class SuccessfulLogin(
    @field:Json(name = "mensaje")
    val mensaje: String,
)
