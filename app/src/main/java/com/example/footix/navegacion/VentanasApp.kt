package com.example.footix.navegacion

sealed class VentanasApp(val ruta: String) {
    object ventanaLogin: VentanasApp("ventanaLogin")
    object ventanaRegistro: VentanasApp("ventanaRegistro")

    object ventanaPartido: VentanasApp("ventanaPartido")

    object ventanaProfile: VentanasApp("ventanaProfile")

    object ventanaCentral: VentanasApp("ventanaCentral")
}