package com.example.footix.ventanas

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.footix.controllers.SocialController
import com.example.footix.controllers.UserController
import com.example.footix.models.AccionSocial
import com.example.footix.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialContent(
    padding: PaddingValues,
    navController: NavController,
    changeTopBarVisibility: () -> Unit
) {
    val socialController = SocialController()
    val userController = UserController()
    var acciones = ArrayList<AccionSocial>()
    var users = HashMap<Int,User>()
    var seguidos = ArrayList<Int>()
    runBlocking {
        val getAccionesSocialesThread = async(Dispatchers.Default) {
            socialController.getAccionesSociales(acciones)
        }
        val getUsersThread = async(Dispatchers.Default) {
            userController.getAllUsers(users)
        }
        val getSeguidosThread = async(Dispatchers.Default) {
            userController.getSeguidos(seguidos, UserController.token?:"")
        }
        getSeguidosThread.await()
        getAccionesSocialesThread.await()
        getUsersThread.await()
    }
    var query by remember {
        mutableStateOf("Busca usuarios")
    }
    var active by remember {
        mutableStateOf(false)
    }
    Column (

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
        content = {
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                leadingIcon =  {
                    Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null)
                },
                query = query,
                onQueryChange = {
                    query = it
                },
                onSearch = {
                },
                active = active,
                onActiveChange = {
                    active=it
                    if (active)
                        query= ""
                    else
                        query = "Busca usuarios"
                    changeTopBarVisibility()}
            ) {
                val filteredUsers = users.values.filter { it.correo.contains(query, ignoreCase = true) }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ){
                    filteredUsers.forEach {
                        if (it.idBd != UserController.user?.idBd)
                            TarjetaUsuarioBuscado(it, seguidos)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (acciones.size > 0) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    for (accion in acciones) {
                        if (accion.usuario in seguidos) {
                            TarjetaAccion(accion, users)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }else{
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.85f)
                ){
                    Text("Aún no sigues a ningún usuario, o estos no han visto ningún partido", textAlign = TextAlign.Center)
                }
            }
        }
    )
}

@Composable
fun TarjetaUsuarioBuscado(user: User, seguidos: ArrayList<Int>) {
    var siguiendo by remember {
        mutableStateOf(false)
    }
    if (seguidos.contains(user.idBd))
        siguiendo = true
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)
    ){
        AsyncImage(
            model = user.fotoPerfil,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = user.correo)
            Spacer(modifier = Modifier.height(10.dp))
            val userController = UserController()
            if (siguiendo){
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(android.graphics.Color.parseColor("#666363"))),
                    onClick = {
                        var borrado = false
                        runBlocking {
                            val deleteSeguidoThread = async(Dispatchers.Default) {
                               borrado = userController.deleteSeguido(user.idBd,UserController.token?:"")
                            }
                            deleteSeguidoThread.await()
                            if(borrado){
                                seguidos.remove(user.idBd)
                                siguiendo=false
                            }
                        }
                    },
                    modifier = Modifier
                        .height(35.dp)
                ) {
                    Text("Siguiendo")
                }
            } else {
                Button(
                    onClick = {
                        var seguido = false
                        runBlocking {
                            val postSeguidoThread = async(Dispatchers.Default) {
                                seguido = userController.postSeguido(user.idBd,UserController.token?:"")
                            }
                            postSeguidoThread.await()
                            if(seguido){
                                seguidos.add(user.idBd)
                                siguiendo=true
                            }
                        }
                    },
                    modifier = Modifier
                        .height(35.dp)
                ) {
                    Text("Seguir")
                }
            }
        }
    }
}

@Composable
fun TarjetaAccion(accion: AccionSocial, listaUsuarios: HashMap<Int,User>) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
        ,modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)
    ) {
        AsyncImage(
            model = listaUsuarios.get(accion.usuario)?.fotoPerfil,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        if (accion.comentario == "" && accion.valoracion ==-1){
            Text(text = listaUsuarios.get(accion.usuario)?.nombre + " ha visto el partido " + accion.nombrePartido)
        } else if (accion.comentario == ""){
            Text(text = listaUsuarios.get(accion.usuario)?.nombre + " ha valorado el partido " + accion.nombrePartido + " con un: " + accion.valoracion)
        }else{
            Text(text = listaUsuarios.get(accion.usuario)?.nombre + " ha comentado sobre el partido " + accion.nombrePartido + ": " + accion.comentario)
        }
    }
}
