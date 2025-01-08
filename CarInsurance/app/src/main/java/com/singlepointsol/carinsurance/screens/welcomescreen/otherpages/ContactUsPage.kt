package com.singlepointsol.carinsurance.screens.welcomescreen.otherpages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.singlepointsol.carinsurance.R

@Composable
fun ContactUsPage(modifier: Modifier) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.custom))
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.support),
            contentDescription = "Support Image"
        )

        Text(text = "This is the Contact Us Page", modifier = Modifier.padding(8.dp))
        Text(text = "Email: support@abzinsurance.com", modifier = Modifier.padding(8.dp))
        Text(text = "Phone: 98490498490", modifier = Modifier.padding(8.dp))
    }
}
