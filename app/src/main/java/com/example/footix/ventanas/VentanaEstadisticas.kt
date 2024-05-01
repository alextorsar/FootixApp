package com.example.footix.ventanas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.footix.controllers.EquiposController
import com.example.footix.controllers.UserController
import com.example.footix.models.Estadisticas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


@Composable
fun EstadisticasContent(padding: PaddingValues, navController: NavController) {
    var userController = UserController()
    var user = UserController.user
    var estadisticas : Estadisticas? = null
    runBlocking {
        val getEstadisticasThread = async(Dispatchers.Default) {
            estadisticas = userController.getEstadisticas(UserController.token?:"")!!
        }
        getEstadisticasThread.await()
    }
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        content = {
            if(estadisticas?.numeroPartidos == 0)
                Text("Aún no has visto ningún partido")
            else{
                var equipo = EquiposController().getEquipoFromId(estadisticas?.equipoMasVisto?:-1)
                Text("Has visto:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(10.dp))
                AnimatedVisibility(visible = true, enter = slideInVertically()) {
                    Text(estadisticas?.numeroPartidos.toString() + " partidos", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text("Has pasado viendo futbol:",fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(10.dp))
                Text(estadisticas?.numeroSegundos.toString() + " segundos", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(30.dp))
                Text("Equipo más visto:",fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(10.dp))
                AsyncImage(
                    model = equipo?.fotoEscudo,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(90.dp)
                        .width(90.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text("Lo has visto:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(10.dp))
                Text(estadisticas?.vecesVisto.toString() + " veces", color = MaterialTheme.colorScheme.primary,fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
            }
        }
    )
}