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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.footix.api.UsersService
import com.example.footix.models.LoginInfo
import com.example.footix.models.SuccessfulLogin
import com.example.footix.navegacion.VentanasApp
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun VentanaLogin(navController: NavController, userServices: UsersService){
    LoginScaffold(navController, userServices)
}

@Composable
fun LoginScaffold(navController: NavController, usersService: UsersService){
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
            LoginContent(padding, navController, usersService, snackbarHostState)
        }
    )
}

@Composable
fun LoginContent(padding: PaddingValues, navController: NavController, usersService: UsersService, snackbarHostState:SnackbarHostState){
    val snackbarScope = rememberCoroutineScope()
    var email by rememberSaveable { mutableStateOf("") }
    var password by remember {
        mutableStateOf("")
    }
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
                    .fillMaxHeight(0.6f)
                    .padding(30.dp)
            ){
                Text(text = "Inicia Sesion", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Email")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = email, onValueChange = { email = it}, maxLines = 1)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Password")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(value = password, onValueChange = { password = it}, maxLines = 1)
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = { if (email != "" && password != ""){
                        val loginInfo = LoginInfo(email, password)
                        usersService.postLogin(loginInfo).enqueue(object : Callback<SuccessfulLogin>{
                            override fun onResponse(call: Call<SuccessfulLogin>, response: Response<SuccessfulLogin>) {
                                if (response.isSuccessful){
                                    navController.navigate(route = VentanasApp.ventanaHome.ruta)
                                }else{
                                    snackbarScope.launch {
                                        snackbarHostState.showSnackbar(message = "Correo o contraseña incorrectos")
                                    }
                                }
                            }
                            override fun onFailure(call: Call<SuccessfulLogin>, t: Throwable) {
                                println("error")
                            }
                        })
                    } },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Iniciar sesión")
                }
                Row {
                    Text(text = "¿No tienes cuenta? ")
                    Text(
                        text = "Regístrate",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            navController.navigate(route = VentanasApp.ventanaRegistro.ruta)
                        }
                    )
                }

            }
        }
    )
}