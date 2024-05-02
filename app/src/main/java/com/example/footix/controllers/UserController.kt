package com.example.footix.controllers

import com.example.footix.api.UsersService
import com.example.footix.models.Estadisticas
import com.example.footix.models.UpdateFieldsInfo
import com.example.footix.models.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserController {
    companion object{
        var user : User? = null
        var token : String? = null
    }

    val usersService = UsersService.instance

    fun getUser(): User?{
        return user
    }

    fun getToken(): String?{
        return token
    }

    fun getUserFromAPI(token:String):User?{
        var userResult:User? = null
        try{
            var userResponse = usersService.getUser(token).execute()
            if(userResponse.isSuccessful) {
                try{
                    userResult = userResponse.body()
                }catch (e:Exception){
                    print(e)
                }
            }
        }catch (e:Exception){
            print(e)
        }
        return userResult
    }

    fun getEstadisticas(token:String):Estadisticas?{
        var estadisticasResult:Estadisticas? = null
        try{
            var estadisticasResponse = usersService.getEstadisticas(token).execute()
            try{
                if(estadisticasResponse.isSuccessful){
                    try {
                        estadisticasResult = estadisticasResponse.body()
                    }catch (e:Exception){
                        print(e)
                    }
                }
            }catch (e:Exception){
                print(e)
            }
        }catch (e:Exception){
            print(e)
        }
        return estadisticasResult
    }

    fun updateUserFields(updateFieldsInfo: UpdateFieldsInfo): User? {
        var userResult:User? = null
        try{
            val userResponse = usersService.putUser(token?:"",updateFieldsInfo).execute()
            if(userResponse.isSuccessful) {
                try{
                    userResult = userResponse.body()
                }catch (e:Exception){
                    print(e)
                }
            }
        }catch (e:Exception){
            print(e)
        }
        return userResult
    }

    fun updateUserPicture(fotoNueva: File):Boolean {
        var correcto = false
        try{
            val requestBody = fotoNueva.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("fotoPerfil", fotoNueva.getName(), requestBody)
            val userResponse = usersService.updateFotoPerfil(token?:"",body).execute()
            if(userResponse.isSuccessful) {
                correcto = true
            }else{
                correcto = false
            }
        }catch (e:Exception){
            correcto = false
        }
        return correcto
    }
}