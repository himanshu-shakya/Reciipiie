package com.reciipiie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.reciipiie.app.common.navigation.Navigation
import com.reciipiie.app.ui.theme.ReciipiieTheme
import com.reciipiie.app.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val parentNavController = rememberNavController()
            val childNavController = rememberNavController()
            ReciipiieTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = White
                ) {
                    Navigation(
                        parentNavController = parentNavController,
                        mainActivity = this,
                        childNavController = childNavController

                    )
                }
            }
        }
    }
}
