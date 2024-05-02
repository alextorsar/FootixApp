package com.example.footix.ventanas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.footix.R
import com.example.footix.controllers.EquiposController
import com.example.footix.controllers.PartidosController
import com.example.footix.controllers.UserController
import com.example.footix.models.Equipo
import com.example.footix.models.Partido
import com.example.footix.navegacion.VentanasApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.time.ZonedDateTime




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomBottomBar(pagerState: PagerState?, navController: NavController) {

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(50.dp)
    ){
        Row (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
        ){
            Box(modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(Alignment.CenterVertically)
                .clickable(onClick = {
                    if (pagerState != null) {
                        val currentPage = pagerState.currentPage
                        if (currentPage != 0) {
                            runBlocking {
                                pagerState.scrollToPage(0)
                            }
                        }
                    } else {
                        navController.navigate(VentanasApp.ventanaCentral.ruta + "/0")
                    }
                })
            ){
                var id: Int
                if(pagerState != null && pagerState.currentPage == 0){
                    id = R.drawable.homepressed
                }else{
                    id = R.drawable.home
                }
                Image(
                    painter = painterResource(id = id),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
            Box(modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(Alignment.CenterVertically)
                .clickable(onClick = {
                    if (pagerState != null) {
                        if (pagerState.currentPage != 1) {
                            runBlocking {
                                pagerState.scrollToPage(1)
                            }
                        }
                    } else {
                        navController.navigate(VentanasApp.ventanaCentral.ruta + "/1")
                    }
                })
            ){
                var id: Int
                if(pagerState != null && pagerState.currentPage == 1){
                    id = R.drawable.socialpressed
                }else{
                    id = R.drawable.social
                }
                Image(
                    painter = painterResource(id = id),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
            Box(modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .align(Alignment.CenterVertically)
                .clickable(onClick = {
                    if (pagerState != null) {
                        if (pagerState.currentPage != 2
                        ) {
                            runBlocking {
                                pagerState.scrollToPage(2)
                            }
                        }
                    } else {
                        navController.navigate(VentanasApp.ventanaCentral.ruta + "/2")
                    }
                })
            ){
                var id: Int
                if(pagerState != null && pagerState.currentPage == 2){
                    id = R.drawable.estadisticaspressed
                }else{
                    id = R.drawable.estadisticas
                }
                Image(
                    painter = painterResource(id = id),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Footix", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            Spacer(modifier = Modifier.width(5.dp))
            AsyncImage(
                model = UserController.user?.fotoPerfil,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clip(shape = CircleShape)
                    .clickable(onClick = {
                        navController.navigate(VentanasApp.ventanaProfile.ruta)
                    })

            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContentJornada(padding: PaddingValues, navController: NavController, jornada: Int){
    val equiposController = EquiposController()
    val partidosController = PartidosController()
    var partidos = ArrayList<Partido>()
    var equipos = equiposController.getEquipos()
    runBlocking {
        val getPartidosThread = async(Dispatchers.Default) {
            partidosController.getPartidosJornadaActual(partidos)
        }
        getPartidosThread.await()
    }
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        content = {
            ListaDePartidos(partidos, equipos, navController,partidosController)
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListaDePartidos(
    partidos: ArrayList<Partido>,
    equipos: HashMap<Int, Equipo>,
    navController: NavController,
    partidosController: PartidosController,
){
    var expanded by remember { mutableStateOf(false) }
    var jornada by remember { mutableIntStateOf(partidos[0].jornada) }
    val partidosState = remember {
        partidos.toMutableStateList()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(1f)
            .fillMaxHeight(0.78f)
            .padding(5.dp)
    ){
        Row (){
            Text(text = "Jornada " + jornada, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.align(Alignment.CenterVertically))
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
                for (i in 1..38){
                    DropdownMenuItem(
                        text = { Text("Jornada " + i) },
                        onClick = {
                            expanded = false
                            jornada = i
                            PartidosController.Partidos.clear()
                            partidosState.clear()
                            partidos.clear()
                            runBlocking {
                                val getPartidosThread = async(Dispatchers.Default) {
                                    partidosController.getPartidosJornada(partidos,i,partidosState)
                                }
                                getPartidosThread.await()
                            }
                        },
                        Modifier.background(MaterialTheme.colorScheme.surface)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        for (partido in partidosState){
            var equipoLocal = equipos[partido.equipoLocal]
            var equipoVisitante = equipos.get(partido.equipoVisitante)
            if (equipoVisitante != null && equipoLocal != null) {
                TarjetaPartido(partido, equipoLocal, equipoVisitante, navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TarjetaPartido(
    partido: Partido,
    equipoLocal: Equipo,
    equipoVisitante: Equipo,
    navController: NavController
) {
    Row (modifier = Modifier
        .height(50.dp)
        .fillMaxWidth()
        .clickable(onClick = {
            navController.navigate(VentanasApp.ventanaPartido.ruta + "/" + partido.idBd)
        })
        ){
        Row(modifier = Modifier
            .weight(1f)
            .wrapContentWidth(Alignment.End)
            .align(Alignment.CenterVertically)
        ) {
            Text(text = equipoLocal.nombre, Modifier.align(Alignment.CenterVertically), fontSize = 14.sp)
            AsyncImage(
                model = equipoLocal.fotoEscudo,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
        }
        if (partido.status == 3){
            Text(text = "  -  ", Modifier.align(Alignment.CenterVertically),fontSize = 15.sp)
        }else if(partido.status == 1){
            Text(text = " " + partido.golesLocal + "-" + partido.golesVisitante + " ", Modifier.align(Alignment.CenterVertically),fontSize = 15.sp)
        }else{
            val fecha = ZonedDateTime.parse(partido.fecha)
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = fecha.dayOfMonth.toString()+"/"+fecha.monthValue,fontSize = 15.sp, textAlign = TextAlign.Center)
                Text(text = fecha.hour.toString()+":"+ (if (fecha.minute < 10) '0' else "") + fecha.minute,fontSize = 15.sp, textAlign = TextAlign.Center)
            }
        }

        Row(modifier = Modifier
            .weight(1f)
            .wrapContentWidth(Alignment.Start)
            .align(Alignment.CenterVertically)
        ){
            AsyncImage(
                model = equipoVisitante.fotoEscudo,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
            Text(text = equipoVisitante.nombre,Modifier.align(Alignment.CenterVertically), fontSize = 14.sp)
        }
    }
    Divider()
}
