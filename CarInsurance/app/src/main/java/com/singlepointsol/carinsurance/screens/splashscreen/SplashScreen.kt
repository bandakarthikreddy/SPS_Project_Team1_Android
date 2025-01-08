package com.singlepointsol.carinsurance.screens.splashscreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.navigation.AuthScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Delay before navigating to the next screen
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(AuthScreen.LOGIN.route) {
            popUpTo(AuthScreen.SPLASH.route) { inclusive = true }
        }
    }

    // Animate the Image and Text positions
    val imageOffset = animateDpAsState(
        targetValue = 0.dp,
        animationSpec = tween(durationMillis = 1000) // Animation duration for the image
    )
    val textOffset = animateDpAsState(
        targetValue = 0.dp,
        animationSpec = tween(durationMillis = 1000) // Animation duration for the text
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image animation: from left to center
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Splash Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .offset(x = imageOffset.value) // Animating image from start to center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Text animation: from bottom to center
            Text(
                text = "Welcome to ABZ Insurance",
                modifier = Modifier
                    .padding(16.dp)
                    .offset(y = textOffset.value), // Animating text from bottom to center
                style = TextStyle(
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
