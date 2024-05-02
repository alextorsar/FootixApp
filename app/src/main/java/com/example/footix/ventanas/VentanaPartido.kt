package com.example.footix.ventanas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.footix.controllers.EquiposController
import com.example.footix.controllers.PartidosController
import com.example.footix.models.Partido

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VentanaPartido(navController: NavController, idPartido: Int?){
    val partidosController = PartidosController()
    val equiposController = EquiposController()
    val partido: Partido = partidosController.getPartidoFromId(idPartido?:-1)
    val equipoLocal = equiposController.getEquipoFromId(partido.equipoLocal)
    val equipoVisitante = equiposController.getEquipoFromId(partido.equipoVisitante)
    var pagerState: PagerState? = null
    Scaffold(
        topBar = {CustomTopBar(navController)},
        content = { padding ->
            PartidoContent(padding, navController)
        },
        bottomBar = {CustomBottomBar(pagerState, navController) }
    )
}

@Composable
fun PartidoContent(padding: PaddingValues, navController: NavController) {

}
