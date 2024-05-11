package com.example.footix.controllers

import com.example.footix.api.SocialServices
import com.example.footix.models.AccionSocial

class SocialController {
    fun getAccionesSociales(acciones: ArrayList<AccionSocial>) {
        var socialServices = SocialServices.instance
        try{
            var accionesResponse = socialServices.getUltimasAccionesSociales(UserController.token?:"").execute()
            if(accionesResponse.isSuccessful){
                for (accion in accionesResponse.body()!!){
                    acciones.add(accion)
                }
            }
        }catch (e:Exception){
            print(e)
        }
    }
}