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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.footix.navegacion.VentanasApp
import com.example.footix.ui.theme.FootixTheme

@Composable
fun emailRegistroInput(){
    var email by remember {
        mutableStateOf("")
    }
    Text(text = "Email")
    Spacer(modifier = Modifier.height(10.dp))
    TextField(value = email, onValueChange = { email = it})
}

@Composable
fun passwordRegistroInput(){
    var password by remember {
        mutableStateOf("")
    }
    Text(text = "Password")
    Spacer(modifier = Modifier.height(10.dp))
    TextField(value = password, onValueChange = { password = it})
}

@Composable
fun NombreregistroInput(){
    var nombre by remember {
        mutableStateOf("")
    }
    Text(text = "Nombre")
    Spacer(modifier = Modifier.height(10.dp))
    TextField(value = nombre, onValueChange = { nombre = it})
}
@Composable
fun DescripcionRegistroInput(){
    var descripcion by remember {
        mutableStateOf("")
    }
    Text(text = "Descripcion")
    Spacer(modifier = Modifier.height(10.dp))
    TextField(value = descripcion, onValueChange = { descripcion = it})
}


@Composable
fun VentanaRegistro(navController: NavController){
    RegistroScaffold(navController)
}

@Composable
fun RegistroScaffold(navController: NavController){
    Scaffold(
        content = { padding ->
            RegistroContent(padding, navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroTopBar(){
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Footix", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleLarge)
            }
        },
    )
}

@Composable
fun RegistroContent(padding: PaddingValues, navController: NavController){
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
                Text(text = "Regístrate", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(30.dp))
                emailRegistroInput()
                passwordRegistroInput()
                NombreregistroInput()
                DescripcionRegistroInput()
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = { /*TODO*/ },
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

