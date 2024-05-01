package com.example.footix.navegacion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.footix.api.UsersService
import com.example.footix.ventanas.VentanaLogin
import com.example.footix.ventanas.VentanaPartido
import com.example.footix.ventanas.VentanaProfile
import com.example.footix.ventanas.VentanaRegistro
import com.example.footix.ventanas.VentanaCentral

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavegacionApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = VentanasApp.ventanaLogin.ruta) {
        composable(route = VentanasApp.ventanaLogin.ruta){
            VentanaLogin(navController, UsersService.instance)
        }
        composable(route = VentanasApp.ventanaRegistro.ruta){
            VentanaRegistro(navController, UsersService.instance)
        }
        composable(route = VentanasApp.ventanaProfile.ruta){
            VentanaProfile(navController)
        }
        composable(route = VentanasApp.ventanaCentral.ruta + "/{idVentana}",
            arguments = listOf(navArgument(name = "idVentana"){ type= NavType.IntType})
        ){
            VentanaCentral(navController, it.arguments?.getInt("idVentana"))
        }
        composable(route = VentanasApp.ventanaPartido.ruta + "/{idPartido}",
            arguments = listOf(navArgument(name = "idPartido"){ type= NavType.IntType})
        ){
            VentanaPartido(navController,it.arguments?.getInt("idPartido"))
        }
    }
}