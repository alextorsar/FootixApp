package com.example.footix.controllers

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.footix.api.PartidosServices
import com.example.footix.models.Partido

class PartidosController {

    companion object{
        var Partidos = ArrayList<Partido>()
    }

    fun getPartidoFromId(id:Int):Partido{
        var encontrado = false
        var partidoResultado = Partidos.get(0)
        var i = 0
        while (!encontrado){
            if (Partidos.get(i).idBd == id){
                encontrado =  true
                partidoResultado = Partidos.get(i)
            }else{
                i++
            }
        }
        return partidoResultado
    }

    fun getPartidosJornada(
        partidos: ArrayList<Partido>,
        jornada: Int,
        partidosState: SnapshotStateList<Partido>
    )
    {
        var partidosServices = PartidosServices.instance
        try{
            var partidosResponse = partidosServices.getPartidosJornada(jornada).execute()
            if(partidosResponse.isSuccessful){
                for (partido in partidosResponse.body()!!){
                    Partidos.add(partido)
                    partidos.add(partido)
                    partidosState.add(partido)
                }
            }
        }catch (e:Exception){
            print(e)
        }
    }

    fun getPartidosJornadaActual(partidos: ArrayList<Partido>){
        var partidosServices = PartidosServices.instance
        try{
            var partidosResponse = partidosServices.getPartidosJornadaActual().execute()
            if(partidosResponse.isSuccessful){
                for (partido in partidosResponse.body()!!){
                    Partidos.add(partido)
                    partidos.add(partido)
                }
            }
        }catch (e:Exception){
            print(e)
        }
    }
}