package com.example.footix.ventanas


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.footix.api.UsersService
import com.example.footix.models.LoginInfo
import com.example.footix.models.RegisterInfo
import com.example.footix.models.SuccessfulLogin
import com.example.footix.models.User
import com.example.footix.navegacion.VentanasApp
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun VentanaRegistro(navController: NavController, usersService: UsersService){
    RegistroScaffold(navController, usersService)
}

@Composable
fun RegistroScaffold(navController: NavController, usersService: UsersService){
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState){ data ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    snackbarData = data,
                )
            }
        },
        content = { padding ->
            RegistroContent(padding, navController, usersService, snackbarHostState )
        }
    )
}


@Composable
fun RegistroContent(
    padding: PaddingValues,
    navController: NavController,
    usersService: UsersService,
    snackbarHostState: SnackbarHostState
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    val snackbarScope = rememberCoroutineScope()
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.8f)
                    .padding(30.dp)
            ){
                Text(text = "Regístrate", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Email")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = email, onValueChange = { email = it}, maxLines = 1)
                Text(text = "Password")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = password, onValueChange = { password = it}, maxLines = 1)
                Text(text = "Nombre")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = nombre, onValueChange = { nombre = it}, maxLines = 1)
                Text(text = "Descripcion")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = descripcion, onValueChange = { descripcion = it}, maxLines = 1)
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        if (email != "" && password != "" && nombre != ""){
                            val registerInfo = RegisterInfo(nombre, email, password, descripcion)
                            usersService.postRegister(registerInfo).enqueue(object :
                                Callback<User> {
                                override fun onResponse(call: Call<User>, response: Response<User>) {
                                    if(response.isSuccessful){
                                        navController.navigate(VentanasApp.ventanaCentral.ruta + "/0")
                                    }else{
                                        snackbarScope.launch {
                                            snackbarHostState.showSnackbar(message = "No se ha podido crear el usuario")
                                        }
                                    }
                                }
                                override fun onFailure(call: Call<User>, t: Throwable) {
                                    println("Error")
                                }

                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Crea una cuenta")
                }
                Row {
                    Text(text = "¿Ya tienes cuenta? ")
                    Text(text = "Inicia sesion", color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navController.navigate(route = VentanasApp.ventanaLogin.ruta)
                    })
                }
            }
        }
    )
}

