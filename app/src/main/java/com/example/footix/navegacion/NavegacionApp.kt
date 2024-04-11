package com.example.footix.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.footix.ventanas.VentanaLogin
import com.example.footix.ventanas.VentanaRegistro

@Composable
fun NavegacionApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = VentanasApp.ventanaLogin.ruta) {
        composable(route = VentanasApp.ventanaLogin.ruta){
            VentanaLogin(navController)
        }
        composable(route = VentanasApp.ventanaRegistro.ruta){
            VentanaRegistro(navController)
        }
    }
}