package com.example.topimagesdemoapp.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.topimagesdemoapp.Routes

@Composable
    fun ScreenMain() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.TopImages.route) {
            composable(Routes.TopImages.route) {
                TopImageScreen(navController)
            }

        }
    }
