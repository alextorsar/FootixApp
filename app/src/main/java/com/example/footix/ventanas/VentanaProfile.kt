package com.example.footix.ventanas

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.footix.api.UsersService
import com.example.footix.controllers.EquiposController
import com.example.footix.controllers.UserController
import com.example.footix.models.UpdateFieldsInfo
import com.example.footix.models.User
import com.example.footix.navegacion.VentanasApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream

@Composable
fun VentanaProfile(navController: NavController){
    val userServices = UsersService.instance
    ProfileScaffold(navController, userServices)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScaffold(navController: NavController, usersService: UsersService){
    var pagerState:PagerState? = null
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
        topBar = { CustomTopBar(navController, true) },
        content = { padding ->
            ProfileContent(padding, navController, usersService, snackbarHostState)
        },
        bottomBar = { CustomBottomBar(
            pagerState = pagerState,
            navController = navController,
            visible = true
        )}

    )
}

fun savePublicFileToAppCacheDir(context: Context, fileUri: Uri): File {
    val appCacheDir = ContextCompat.getExternalCacheDirs(context)
        .filterNotNull()
        .first()
        .also { it.mkdirs() }
    val file = File(appCacheDir, "temp_file.jpg")
    context.contentResolver.openInputStream(fileUri)!!.use { input ->
        FileOutputStream(file).use { output ->
            output.write(input.readBytes())
        }
    }
    return file
}

@Composable
fun ProfileContent(
    padding: PaddingValues,
    navController: NavController,
    usersService: UsersService,
    snackbarHostState: SnackbarHostState
) {
    val contexto = LocalContext.current
    val snackbarScope = rememberCoroutineScope()
    var user = UserController.user
    var equipos = EquiposController.Equipos
    var urlEscudo : String? = ""
    if (user != null) {
        if (user.equipoFavorito != -1){
            urlEscudo = equipos[user.equipoFavorito]?.fotoEscudo ?: ""
        }
    }
    var email by remember { mutableStateOf(user?.correo?:"") }
    var nombre by remember { mutableStateOf(user?.nombre?:"") }
    var descripcion by remember { mutableStateOf(user?.descripcion?:"") }
    var equipoFavorito by remember { mutableIntStateOf(user?.equipoFavorito?:-1) }
    var fotoEquipoUrl by remember { mutableStateOf(urlEscudo) }
    var selectedImages = remember { ArrayList<Uri>().toMutableStateList() }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> if ( uri != null) if(selectedImages.size > 0) selectedImages[0] = uri else selectedImages.add(uri) else selectedImages.removeAt(0)  }
    )
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(0.7f)
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        content = {
            AsyncImage(
                model = if (selectedImages.isNotEmpty()) selectedImages[0] else user?.fotoPerfil,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clip(CircleShape)
                    .clickable(onClick={
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    } )
            )
            Text(text = "Foto de perfil")
            Spacer(modifier = Modifier
                .height(15.dp)
                .fillMaxWidth())
            Text(text = "Email")
            Spacer(modifier = Modifier.height(2.5.dp))
            TextField(value = email, onValueChange = { email = it}, maxLines = 1,modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(2.5.dp))
            Text(text = "Nombre")
            Spacer(modifier = Modifier.height(2.5.dp))
            TextField(value = nombre, onValueChange = { nombre = it}, maxLines = 1,modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(2.5.dp))
            Text(text = "Descripcion")
            Spacer(modifier = Modifier.height(2.5.dp))
            TextField(value = descripcion, onValueChange = { descripcion = it}, maxLines = 3,modifier = Modifier.fillMaxWidth())
            Row (){
                var expanded by remember { mutableStateOf(false) }
                Text(text = "Equipo favorito", modifier = Modifier.align(Alignment.CenterVertically))
                IconButton(onClick = { expanded = !expanded }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .requiredSizeIn(maxHeight = 200.dp)
                        .background(MaterialTheme.colorScheme.surface),
                ) {
                    for (equipo in equipos.values){
                        DropdownMenuItem(
                            text = { Text(equipo.nombre) },
                            onClick = { fotoEquipoUrl = equipo.fotoEscudo; equipoFavorito=equipo.idBd; expanded=false},
                            Modifier.background(MaterialTheme.colorScheme.surface)
                        )
                    }
                }
            }
            if(fotoEquipoUrl != ""){
                AsyncImage(
                    model = fotoEquipoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                )
            }else{
                Box(modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)){
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    var correcto = true
                    val userController = UserController()
                    if(nombre!=user?.nombre || email!=user.correo || descripcion!=user.descripcion || equipoFavorito != user.equipoFavorito){
                        correcto = false
                        val updateFieldsInfo = UpdateFieldsInfo(nombre,email,descripcion,equipoFavorito)
                        var updatedUser: User? = null
                        runBlocking {
                            val updateProfileThread = async(Dispatchers.Default) {
                                updatedUser = userController.updateUserFields(updateFieldsInfo)
                            }
                            updateProfileThread.await()
                        }
                        if (updatedUser != null) {
                            UserController.user = updatedUser
                            correcto = true
                        }
                    }
                    if(correcto && selectedImages.isNotEmpty()){
                        correcto = false
                        var fotoNueva = savePublicFileToAppCacheDir(contexto,selectedImages[0])
                        print(fotoNueva)
                        runBlocking {
                            val updateProfilePictureThread = async(Dispatchers.Default) {
                                correcto = userController.updateUserPicture(fotoNueva)
                            }
                            updateProfilePictureThread.await()
                        }
                        if(correcto)
                            UserController.user?.fotoPerfil = selectedImages[0].toString()

                    }
                    if(correcto){
                        navController.navigate(VentanasApp.ventanaProfile.ruta)
                    }else{
                        snackbarScope.launch {
                            snackbarHostState.showSnackbar(message = "No se ha podido actualizar")
                        }
                    }

                },
                modifier = Modifier
                .fillMaxWidth()
            ) {
                Text(text = "Actualizar perfil")
            }
        }
    )


}
