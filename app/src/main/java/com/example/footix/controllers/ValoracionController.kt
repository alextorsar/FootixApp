package com.example.footix.controllers

import com.example.footix.api.ValoracionesServices
import com.example.footix.api.VisualizacionesServices
import com.example.footix.models.InfoPartido
import com.example.footix.models.InfoValoracion
import com.example.footix.models.Valoracion
import com.example.footix.models.Visualizacion

class ValoracionController {
    fun getValoracionesPartido(valoraciones: ArrayList<Valoracion>, idPartido: Int){
        val valoracionesInstance = ValoracionesServices.instance
        try{
            var valoracionesResponse = valoracionesInstance.getValoraciones(UserController.token?:"",idPartido).execute()
            if(valoracionesResponse.isSuccessful){
                for (valoracion in valoracionesResponse.body()!!){
                    valoraciones.add(valoracion)
                }
            }
        }catch (e:Exception){
            print(e)
        }
    }

    fun postValoracion(idPartido: Int, valoracion: Int):Valoracion?{
        var infoValoracion = InfoValoracion(idPartido, valoracion)
        var resultado:Valoracion? = null
        val valoracionInstance = ValoracionesServices.instance
        try{
            var valoracionResponse = valoracionInstance.postValoracion(UserController.token?:"",infoValoracion).execute()
            if(valoracionResponse.isSuccessful){
                resultado = valoracionResponse.body()
            }
        }catch (e: Exception){
            print(e)
        }
        return resultado
    }

    fun deleteVisualizacion(idPartido: Int):Boolean{
        var borrado = false
        val valoracionesInstance = ValoracionesServices.instance
        try{
            var valoracionResponse = valoracionesInstance.deleteValoracion(UserController.token?:"",idPartido).execute()
            if(valoracionResponse.isSuccessful){
                borrado = true
            }
        }catch (e: Exception){
            print(e)
        }
        return borrado
    }
}