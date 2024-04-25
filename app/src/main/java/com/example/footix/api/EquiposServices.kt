package com.example.footix.api

import com.example.footix.models.Equipo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface EquiposServices {
    companion object{
        val instance = Retrofit.Builder().baseUrl("https://d0a0-85-54-210-196.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build().create(EquiposServices::class.java)
    }

    @Headers("ngrok-skip-browser-warning: 69420")
    @GET("equipo")
    suspend fun getEquipos():List<Equipo>
}