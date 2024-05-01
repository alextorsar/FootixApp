package com.example.footix.api

import com.example.footix.models.Equipo
import com.example.footix.models.LoginInfo
import com.example.footix.models.Partido
import com.example.footix.models.SuccessfulLogin
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PartidosServices {
    companion object{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val instance = Retrofit.Builder().baseUrl("https://f375-85-54-210-196.ngrok-free.app/api/partido/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build().create(PartidosServices::class.java)
    }

    @Headers("ngrok-skip-browser-warning: 69420")
    @GET("jornada/actual/")
    fun getPartidosJornadaActual():Call<List<Partido>>

    @Headers("ngrok-skip-browser-warning: 69420")
    @GET("jornada/{numJornada}/")
    fun getPartidosJornada(@Path("numJornada") jornada: Int):Call<List<Partido>>
}