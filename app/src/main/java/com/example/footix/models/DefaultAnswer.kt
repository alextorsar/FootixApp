package com.example.footix.models

import com.squareup.moshi.Json

data class DefaultAnswer(
    @field:Json(name = "mensaje")
    val mensaje: String,
)
