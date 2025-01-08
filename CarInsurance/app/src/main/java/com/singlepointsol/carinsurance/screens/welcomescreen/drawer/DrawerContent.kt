package com.singlepointsol.carinsurance.screens.welcomescreen.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.singlepointsol.carinsurance.R
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()

    )

    Column(
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {


        Spacer(modifier = Modifier.height(60.dp))
        // Card containing menu items
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.drawercolor)),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextButton(onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate("contactus")
                }) {
                    Text("Contact Us")
                }
                TextButton(onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate("forms")
                }) {
                    Text("Forms")
                }
            }
        }
    }
}