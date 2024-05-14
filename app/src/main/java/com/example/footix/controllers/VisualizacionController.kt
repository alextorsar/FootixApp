package com.example.footix.controllers

import com.example.footix.api.VisualizacionesServices
import com.example.footix.models.InfoPartido
import com.example.footix.models.Resenia
import com.example.footix.models.Visualizacion

class VisualizacionController {
    fun getVisualizacionesPartido(visualizaciones: ArrayList<Visualizacion>, idPartido: Int){
        val visualizacionesInstance = VisualizacionesServices.instance
        try{
            var visualizacionesResponse = visualizacionesInstance.getVisualizaciones(UserController.token?:"",idPartido).execute()
            if(visualizacionesResponse.isSuccessful){
                for (visualizacion in visualizacionesResponse.body()!!){
                    visualizaciones.add(visualizacion)
                }
            }
        }catch (e:Exception){
            print(e)
        }
    }

    fun postVisualizacion(idPartido: Int):Visualizacion?{
        var infoPartido = InfoPartido(idPartido)
        var resultado:Visualizacion? = null
        val visualizacionesInstance = VisualizacionesServices.instance
        try{
            var visualizacionResponse = visualizacionesInstance.postVisualizacion(UserController.token?:"",infoPartido).execute()
            if(visualizacionResponse.isSuccessful){
                resultado = visualizacionResponse.body()
            }
        }catch (e: Exception){
            print(e)
        }
        return resultado
    }

    fun deleteVisualizacion(idPartido: Int):Boolean{
        var borrado = false
        val visualizacionesInstance = VisualizacionesServices.instance
        try{
            var visualizacionResponse = visualizacionesInstance.deleteVisualizacion(UserController.token?:"",idPartido).execute()
            if(visualizacionResponse.isSuccessful){
                borrado = true
            }
        }catch (e: Exception){
            print(e)
        }
        return borrado
    }
}