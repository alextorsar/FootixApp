package com.example.footix.ventanas

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.footix.api.UsersService
import com.example.footix.controllers.EquiposController
import com.example.footix.controllers.PartidosController
import com.example.footix.models.Equipo
import com.example.footix.models.Partido

@Composable
fun VentanaPartido(navController: NavController, idPartido: Int?){
    try{
        val partidosController = PartidosController()
        val equiposController = EquiposController()
        val partido: Partido = partidosController.getPartidoFromId(idPartido?:-1)
        val equipoLocal = equiposController.getEquipoFromId(partido.equipoLocal)
        val equipoVisitante = equiposController.getEquipoFromId(partido.equipoVisitante)

    }catch (e: Exception){
        print(e)
    }
}