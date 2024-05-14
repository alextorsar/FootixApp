package com.example.footix.api

import com.example.footix.models.DefaultAnswer
import com.example.footix.models.InfoResenia
import com.example.footix.models.InfoValoracion
import com.example.footix.models.Resenia
import com.example.footix.models.Valoracion
import com.example.footix.models.Visualizacion
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ReseniasServices {
    companion object{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val instance = Retrofit.Builder().baseUrl("https://3aeb-85-54-210-196.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build().create(ReseniasServices::class.java)
    }

    @Headers("ngrok-skip-browser-warning: 69420")
    @GET("resenia/partido/{idPartido}/")
    fun getResenias(@Header("Cookie") token:String, @Path("idPartido") idPartido: Int): Call<List<Resenia>>

    @Headers("ngrok-skip-browser-warning: 69420")
    @POST("resenia/")
    fun postResenia(@Header("Cookie") token:String, @Body infoResenia : InfoResenia) : Call<Resenia>

}