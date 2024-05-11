package com.example.footix.ventanas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VentanaCentral(navController: NavController, currentPage: Int?){

        CentralScaffold(navController,currentPage?:0)

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CentralScaffold(navController: NavController, currentPage: Int) {

    val pagerState = rememberPagerState(initialPage = currentPage) {
        3
    }

    var visible by remember {
        mutableStateOf(true)
    }

    val changeTopBarVisibility : () -> Unit = {
        visible = !visible
    }
    Scaffold(
        topBar = {CustomTopBar(navController,visible)},
        content = { padding ->
            HorizontalPager(state = pagerState) {currentPage ->
                if(currentPage == 0){
                    HomeContentJornada(padding = padding, navController = navController)
                }else if (currentPage ==1){
                    SocialContent(padding = padding, navController = navController, changeTopBarVisibility)
                }else{
                    EstadisticasContent(padding = padding, navController = navController)
                }
            }
        },
        bottomBar = {CustomBottomBar(pagerState, navController, visible) }
    )
}
