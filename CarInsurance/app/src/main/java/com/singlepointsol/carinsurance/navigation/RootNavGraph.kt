package com.singlepointsol.carinsurance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.singlepointsol.carinsurance.screens.welcomescreen.WelcomeScreen

@Composable
fun RootNavGraph(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Graph.AUTHENTICATION,
        route = Graph.ROOT
    ) {
        authNavGraph(navController)
        composable(route = Graph.MAIN_SCREEN_PAGE) {
            WelcomeScreen(modifier = Modifier, navController = navController)
        }
    }
}


object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val MAIN_SCREEN_PAGE = "main_screen_graph"
}