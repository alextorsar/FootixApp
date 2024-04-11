package com.example.footix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.footix.navegacion.NavegacionApp
import com.example.footix.ui.theme.FootixTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootixTheme {
                NavegacionApp()
            }
        }
    }
}

@Preview
@Composable
fun MainActivityPreview(){
    FootixTheme {
        NavegacionApp()
    }
}