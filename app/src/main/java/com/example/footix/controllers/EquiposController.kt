package com.example.footix.controllers

import com.example.footix.api.EquiposServices
import com.example.footix.models.Equipo

class EquiposController {
    companion object {
        var Equipos = HashMap<Int, Equipo>()
    }

    fun getEquiposFromApi(equiposServices: EquiposServices, equipos: HashMap<Int,Equipo>){
        try {
            var equiposResponse = equiposServices.getEquipos().execute()
            if (equiposResponse.isSuccessful){
                for(equipo in equiposResponse.body()!!){
                    equipos[equipo.idBd] = equipo
                    Equipos[equipo.idBd] = equipo
                }
            }
        }catch (e: Exception){
            print(e)
        }
    }

    fun getEquipos(): HashMap<Int, Equipo> {
        return Equipos
    }

    fun getEquipoFromId(id: Int):Equipo?{
        return Equipos[id]
    }

}