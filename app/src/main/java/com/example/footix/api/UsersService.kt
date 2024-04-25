package com.example.footix.api

import com.example.footix.models.LoginInfo
import com.example.footix.models.SuccessfulLogin
import com.example.footix.models.User
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface UsersService {
    companion object{
        val instance = Retrofit.Builder().baseUrl("https://d0a0-85-54-210-196.ngrok-free.app/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build().create(UsersService::class.java)
    }

    @GET("user/")
    suspend fun getUser(): User

    @POST("login/")
    fun postLogin(@Body loginInfo: LoginInfo): Call<SuccessfulLogin>

}