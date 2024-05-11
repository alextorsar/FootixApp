package com.example.footix.api

import com.example.footix.models.DefaultAnswer
import com.example.footix.models.Estadisticas
import com.example.footix.models.LoginInfo
import com.example.footix.models.RegisterInfo
import com.example.footix.models.Seguido
import com.example.footix.models.SeguidoInfo
import com.example.footix.models.SuccessfulLogin
import com.example.footix.models.UpdateFieldsInfo
import com.example.footix.models.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path


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

    @GET("seguido/")
    fun getSeguidos(@Header("Cookie") token:String): Call<List<Seguido>>

    @DELETE("seguido/{idUser}/")
    fun deleteSeguido(@Header("Cookie") token:String, @Path("idUser") idUser: Int):Call<DefaultAnswer>

    @POST("seguido/")
    fun postSeguido(@Header("Cookie") token:String, @Body idUser: SeguidoInfo):Call<Seguido>

    @GET("user/")
    fun getUser(@Header("Cookie") token:String): Call<User>

    @GET("users/")
    fun getUsers(): Call<List<User>>

    @PUT("user/")
    fun putUser(@Header("Cookie") token:String, @Body updateInfo:UpdateFieldsInfo): Call<User>

    @POST("login/")
    fun postLogin(@Body loginInfo: LoginInfo): Call<SuccessfulLogin>

    @POST("register/")
    fun postRegister(@Body registerInfo: RegisterInfo): Call<User>

    @GET("estadisticas/")
    fun getEstadisticas(@Header("Cookie") token:String): Call<Estadisticas>

    @Multipart
    @PUT("user/")
    fun updateFotoPerfil(@Header("Cookie") token:String, @Part fotoNueva: MultipartBody.Part): Call<User>
}