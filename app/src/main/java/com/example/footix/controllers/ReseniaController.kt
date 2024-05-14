package com.example.footix.controllers

import com.example.footix.api.ReseniasServices
import com.example.footix.api.VisualizacionesServices
import com.example.footix.models.InfoResenia
import com.example.footix.models.Resenia

class ReseniaController {
    fun getReseniasController(resenias: ArrayList<Resenia>, idPartido: Int){
        val reseniasServices = ReseniasServices.instance
        try {
            var reseniasResponse =  reseniasServices.getResenias(UserController.token?:"", idPartido).execute()
            if (reseniasResponse.isSuccessful){
                for (resenia in reseniasResponse.body()!!){
                    resenias.add(resenia)
                }
            }
        }catch (e:Exception){
            print(e)
        }
    }

    fun postResenia(idPartido: Int, comentario:String):Resenia?{
        var infoResenia = InfoResenia(idPartido,comentario)
        var resultado:Resenia? = null
        val reseniasInstance = ReseniasServices.instance
        try{
            var reseniaResponse = reseniasInstance.postResenia(UserController.token?:"",infoResenia).execute()
            if(reseniaResponse.isSuccessful){
                resultado = reseniaResponse.body()
            }
        }catch (e: Exception){
            print(e)
        }
        return resultado
    }
}