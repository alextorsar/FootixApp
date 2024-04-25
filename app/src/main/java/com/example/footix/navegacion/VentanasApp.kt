package com.example.footix.navegacion

sealed class VentanasApp(val ruta: String) {
    object ventanaLogin: VentanasApp("ventanaLogin")
    object ventanaRegistro: VentanasApp("ventanaRegistro")

    object ventanaHome: VentanasApp("ventanaHome")
}