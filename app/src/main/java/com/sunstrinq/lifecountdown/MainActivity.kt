package com.sunstrinq.lifecountdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sunstrinq.lifecountdown.ui.screen.LifeCountdownScreen
import com.sunstrinq.lifecountdown.ui.theme.LifeCountdownTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeCountdownTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LifeCountdownScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
