package com.ghaldanurzahrah0016.assessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ghaldanurzahrah0016.assessment1.ui.screen.MainScreen
import com.ghaldanurzahrah0016.assessment1.ui.theme.Assessment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assessment1Theme {
                MainScreen()
            }
        }
    }
}