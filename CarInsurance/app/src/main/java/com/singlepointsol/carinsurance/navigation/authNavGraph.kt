package com.singlepointsol.carinsurance.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.singlepointsol.carinsurance.screens.auth.ForgotScreen
import com.singlepointsol.carinsurance.screens.auth.LoginScreen
import com.singlepointsol.carinsurance.screens.auth.SignupScreen
import com.singlepointsol.carinsurance.screens.splashscreen.SplashScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SPLASH.route
    ) {
        composable(route = AuthScreen.SPLASH.route) {
            SplashScreen(navController)
        }
        composable(route = AuthScreen.LOGIN.route) {
            LoginScreen(navController)
        }
        composable(route = AuthScreen.FORGOT.route) {
            ForgotScreen(navController)
        }
        composable(route = AuthScreen.SIGNUP.route) {
            SignupScreen(navController)
        }
    }
}


sealed class AuthScreen(val route: String) {
    object SPLASH: AuthScreen("SPLASH")
    object LOGIN: AuthScreen("LOGIN")
    object FORGOT: AuthScreen("FORGOT")
    object SIGNUP: AuthScreen("SIGNUP")
}