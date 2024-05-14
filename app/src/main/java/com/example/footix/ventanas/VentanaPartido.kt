package com.example.footix.ventanas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.footix.controllers.EquiposController
import com.example.footix.controllers.PartidosController
import com.example.footix.controllers.ReseniaController
import com.example.footix.controllers.UserController
import com.example.footix.controllers.ValoracionController
import com.example.footix.controllers.VisualizacionController
import com.example.footix.models.Equipo
import com.example.footix.models.Partido
import com.example.footix.models.Resenia
import com.example.footix.models.User
import com.example.footix.models.Valoracion
import com.example.footix.models.Visualizacion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VentanaPartido(navController: NavController, idPartido: Int?){
    val visualizacionController = VisualizacionController()
    var visualizaciones = ArrayList<Visualizacion>()
    val valoracionController = ValoracionController()
    var valoraciones = ArrayList<Valoracion>()
    val reseniaController = ReseniaController()
    var resenias = ArrayList<Resenia>()
    val partidosController = PartidosController()
    var users = HashMap<Int,User>()
    val userController = UserController()
    val equiposController = EquiposController()
    val partido: Partido = partidosController.getPartidoFromId(idPartido?:-1)
    val equipoLocal = equiposController.getEquipoFromId(partido.equipoLocal)
    val equipoVisitante = equiposController.getEquipoFromId(partido.equipoVisitante)
    runBlocking {
        val getVisualizocionesThread = async(Dispatchers.Default) {
            visualizacionController.getVisualizacionesPartido(visualizaciones,idPartido?:-1)
        }
        val getValoracionesThread = async(Dispatchers.Default) {
            valoracionController.getValoracionesPartido(valoraciones,idPartido?:-1)
        }
        val getReseniasThread = async(Dispatchers.Default) {
            reseniaController.getReseniasController(resenias,idPartido?:-1)
        }
        val getUsersThread = async(Dispatchers.Default) {
            userController.getAllUsers(users)
        }
        getVisualizocionesThread.await()
        getValoracionesThread.await()
        getReseniasThread.await()
        getUsersThread.await()
    }
    var pagerState:PagerState? = null
    Scaffold(
        topBar = {CustomTopBar(navController, true)},
        content = { padding ->
            PartidoContent(
                padding = padding,
                navController = navController,
                visualizaciones = visualizaciones,
                valoraciones = valoraciones,
                resenias = resenias,
                users = users,
                partido = partido,
                equipoLocal =  equipoLocal,
                equipoVisitante = equipoVisitante
            )
        },
        bottomBar = {CustomBottomBar(pagerState, navController, true) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PartidoContent(
    padding: PaddingValues,
    navController: NavController,
    visualizaciones: ArrayList<Visualizacion>,
    valoraciones: ArrayList<Valoracion>,
    resenias: ArrayList<Resenia>,
    users: HashMap<Int, User>,
    partido: Partido,
    equipoLocal: Equipo?,
    equipoVisitante: Equipo?
) {
    val pagerMatchState = rememberPagerState() {
        3
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(90.dp))
        Column(modifier = Modifier
            .height(170.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            {
                AsyncImage(
                    model = equipoLocal?.fotoEscudo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                if (partido.status == 1)
                    Text(text = (partido.golesLocal.toString() + " - " + partido.golesVisitante), fontSize = 50.sp)
                else
                    Text(text = ("  -  "), fontSize = 50.sp)
                Spacer(modifier = Modifier.width(20.dp))
                AsyncImage(
                    model = equipoVisitante?.fotoEscudo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .fillMaxWidth()) {
                Text(text = partido.estadio, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .fillMaxWidth()) {
                val fecha = ZonedDateTime.parse(partido.fecha).withZoneSameInstant(ZoneId.of("Europe/Madrid"))
                if(partido.status == 3)
                    Text("Sin fecha asignada")
                else
                    Text(text = fecha.dayOfMonth.toString() + "/" +  fecha.monthValue + "/" + fecha.year + "\t" + fecha.hour + ":" + (if (fecha.minute < 10) '0' else "") + fecha.minute)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85F)
        ) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(0.dp)
            )
            {
                Text(text = "Visualizaciones", color = if (pagerMatchState.currentPage == 0) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.width(5.dp))
                Divider(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .height(20.dp)
                        .width(2.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Valoraciones",color = if (pagerMatchState.currentPage == 1) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.width(5.dp))
                Divider(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .height(20.dp)
                        .width(2.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text= "Reseñas",color = if (pagerMatchState.currentPage == 2) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onPrimary)
            }
            HorizontalPager(state = pagerMatchState,
                Modifier
                    .padding(0.dp)
            ) { currentPage ->
                if(currentPage == 0){
                    VisualizacionesContent(padding = padding, visualizaciones, users, partido.idBd)
                }else if (currentPage ==1){
                    ValoracionesContent(padding = padding, valoraciones, users,partido.idBd)
                }else{
                    ReseniasContent(padding = padding, resenias, users,partido.idBd)
                }
            }
        }
    }
}

@Composable
fun ReseniasContent(
    padding: PaddingValues,
    resenias: ArrayList<Resenia>,
    users: HashMap<Int, User>,
    idPartido: Int
) {
    val reseniaController = ReseniaController()
    var reseniaState by remember { mutableStateOf("Escribe una reseña") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
        if (resenias.size > 0) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.68F)
                    .verticalScroll(rememberScrollState()),
                content = {
                    for (reseniaItem in resenias) {
                        TarjetaResenia(resenia = reseniaItem, listaUsuarios = users)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7F)
                    .padding(3.dp)
                    .verticalScroll(rememberScrollState()),
                content = {
                    Text(text = "El partido aún no tiene reseñas")
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        TextField(modifier = Modifier.fillMaxWidth(0.9F), value = reseniaState, onValueChange = { reseniaState = it}, maxLines = 1)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            onClick = {
                var resenia : Resenia? = null
                runBlocking {
                    val postReseniaThread = async(Dispatchers.Default) {
                        resenia = reseniaController.postResenia(idPartido,reseniaState)
                    }
                    postReseniaThread.await()
                }
                if(resenia != null){
                    resenias.add(resenia!!)
                }
                reseniaState = "Escribe una reseña"
            }) {
            Text(text = "Enviar reseña")
        }

    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun ValoracionesContent(
    padding: PaddingValues,
    valoracionesList: ArrayList<Valoracion>,
    users: HashMap<Int, User>,
    idPartido: Int
) {
    var valorado by remember { mutableStateOf(false) }
    var valoracionUsuario:Valoracion? = null
    var valoracion by remember { mutableStateOf(0) }
    var valoraciones = remember {
        valoracionesList.toMutableStateList()
    }
    for (valoracionItem in valoraciones){
        if (valoracionItem.usuario == UserController.user?.idBd) {
            valoracionUsuario = valoracionItem
            valorado = true
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (valoraciones.size > 0) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.73F)
                    .padding(3.dp)
                    .verticalScroll(rememberScrollState()),
                content = {
                    for (valoracionItem in valoraciones) {
                        TarjetaValoracion(valoracion = valoracionItem, listaUsuarios = users)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.73F)
                    .padding(3.dp)
                    .verticalScroll(rememberScrollState()),
                content = {
                    Text(text = "El partido aún no tiene valoraciones")
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        StarRatingBar(
            maxStars = 5,
            rating = valoracion,
            onRatingChanged = {
                valoracion = it
            }
        )
        Button(
            onClick = {
                val valoracionController = ValoracionController()
                if (valorado){
                    var borrado = false
                      runBlocking{
                          val deleteValoracionThread = async(Dispatchers.Default) {
                              borrado = valoracionController.deleteVisualizacion(idPartido)
                          }
                          deleteValoracionThread.await()
                      }
                    if (borrado){
                        valoraciones.remove(valoracionUsuario)
                        print("")
                    }
                }
                runBlocking {
                    val postValoracionThread = async(Dispatchers.Default) {
                        valoracionUsuario = valoracionController.postValoracion(idPartido,valoracion)
                    }
                    postValoracionThread.await()
                }
                if(valoracionUsuario!=null){
                    valoraciones.add(valoracionUsuario!!)
                    valorado = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            colors = if (valorado) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface) else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            if (valorado)
                Text(text = "Cambiar valoración")
            else
                Text(text = "Enviar valoración")
        }
    }
}



@Composable
fun VisualizacionesContent(
    padding: PaddingValues,
    visualizaciones: ArrayList<Visualizacion>,
    users: HashMap<Int, User>,
    idPartido: Int
) {
    val visualizacionController = VisualizacionController()
    var visto by remember { mutableStateOf(false) }
    var visualizacionUsuario : Visualizacion? = null
    for (visualizacion in visualizaciones){
        if(visualizacion.usuario == UserController.user?.idBd){
            visto = true
            visualizacionUsuario = visualizacion
        }
    }
    Column(modifier = Modifier.fillMaxSize()){
        if(visualizaciones.size > 0) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85F)
                    .padding(3.dp)
                    .verticalScroll(rememberScrollState()),
                content = {
                    for (visualizacion in visualizaciones) {
                        TarjetaVisualizacion(visualizacion = visualizacion, listaUsuarios = users)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            )
        }else{
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85F)
                    .padding(3.dp)
                    .verticalScroll(rememberScrollState()),
                content = {
                    Text(text = "El partido aún no tiene visualizaciones")
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                if(!visto){
                    var nuevaVisualizacion:Visualizacion? = null
                    runBlocking {
                        val postVisualizacionThread = async(Dispatchers.Default) {
                            nuevaVisualizacion = visualizacionController.postVisualizacion(idPartido)
                        }
                        postVisualizacionThread.await()
                    }
                    if(nuevaVisualizacion != null){
                        visto = true
                        visualizaciones.add(nuevaVisualizacion!!)
                        visualizacionUsuario = nuevaVisualizacion
                    }
                }else{
                    var borrado = false
                    runBlocking {
                        val deleteVisualizacionThread = async(Dispatchers.Default) {
                            borrado = visualizacionController.deleteVisualizacion(idPartido)
                        }
                        deleteVisualizacionThread.await()
                    }

                    if(borrado){
                        visto = false
                        visualizaciones.remove(visualizacionUsuario)
                        visualizacionUsuario = null
                    }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            colors = if (visto) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface) else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            if (visto)
                Text(text = "Marcar como no visto")
            else
                Text(text = "Marcar como visto")
        }
    }
}

@Composable
fun TarjetaVisualizacion(visualizacion: Visualizacion, listaUsuarios: HashMap<Int,User>) {
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
            model = listaUsuarios.get(visualizacion.usuario)?.fotoPerfil,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = listaUsuarios.get(visualizacion.usuario)?.nombre + " ha visto el partido")
    }
}

@Composable
fun TarjetaValoracion(valoracion: Valoracion, listaUsuarios: HashMap<Int, User>) {
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
            model = listaUsuarios.get(valoracion.usuario)?.fotoPerfil,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = listaUsuarios.get(valoracion.usuario)?.nombre + " ha valorado el partido con: " + valoracion.valoracion)
    }
}

@Composable
fun TarjetaResenia(resenia: Resenia, listaUsuarios: HashMap<Int, User>) {
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
            model = listaUsuarios.get(resenia.usuario)?.fotoPerfil,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = listaUsuarios.get(resenia.usuario)?.nombre + " ha comentado: " + resenia.comentario)
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
            .fillMaxHeight(0.5F),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) MaterialTheme.colorScheme.primary else Color(android.graphics.Color.parseColor("#BDBBBB"))
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i)
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}
