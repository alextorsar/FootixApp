package com.example.footix.api

import com.example.footix.models.DefaultAnswer
import com.example.footix.models.Visualizacion
import com.example.footix.models.InfoPartido
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface VisualizacionesServices {
    companion object{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val instance = Retrofit.Builder().baseUrl("https://3aeb-85-54-210-196.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build().create(VisualizacionesServices::class.java)
    }

    @Headers("ngrok-skip-browser-warning: 69420")
    @GET("visualizacion/partido/{idPartido}/")
    fun getVisualizaciones(@Header("Cookie") token:String, @Path("idPartido") idPartido: Int): Call<List<Visualizacion>>

    @Headers("ngrok-skip-browser-warning: 69420")
    @POST("visualizacion/")
    fun postVisualizacion(@Header("Cookie") token:String, @Body idPartido : InfoPartido) : Call<Visualizacion>

    @Headers("ngrok-skip-browser-warning: 69420")
    @DELETE("visualizacion/partido/{idPartido}/")
    fun deleteVisualizacion(@Header("Cookie") token:String, @Path("idPartido") idPartido: Int): Call<DefaultAnswer>
}