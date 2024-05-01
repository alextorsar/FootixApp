package com.example.footix.api

import com.example.footix.models.Estadisticas
import com.example.footix.models.LoginInfo
import com.example.footix.models.RegisterInfo
import com.example.footix.models.SuccessfulLogin
import com.example.footix.models.UpdateFieldsInfo
import com.example.footix.models.User
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
import retrofit2.http.PUT


interface UsersService {

    companion object{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val instance = Retrofit.Builder().baseUrl("https://f375-85-54-210-196.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build().create(UsersService::class.java)
    }

    @GET("user/")
    fun getUser(@Header("Cookie") token:String): Call<User>

    @PUT("user/")
    fun putUser(@Header("Cookie") token:String, @Body updateInfo:UpdateFieldsInfo): Call<User>

    @POST("login/")
    fun postLogin(@Body loginInfo: LoginInfo): Call<SuccessfulLogin>

    @POST("register/")
    fun postRegister(@Body registerInfo: RegisterInfo): Call<User>

    @GET("estadisticas/")
    fun getEstadisticas(@Header("Cookie") token:String): Call<Estadisticas>

}