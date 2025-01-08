package com.singlepointsol.carinsurance.screens.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.singlepointsol.carinsurance.R

@Composable
fun ScreenContent(modifier: Modifier){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.custom))
    )

    Column(
        modifier = Modifier
            .fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.mainpage_image),
            contentDescription = "Main Page Image",
            modifier = Modifier
                .width(450.dp)
                .height(450.dp)
                .padding(start = 8.dp, end = 8.dp),
            alignment = Alignment.Center)


        Spacer(modifier = Modifier.height(1.dp))

        Text(
            "Insuring Your Drive, Securing Your Peace of Mind",
            style = TextStyle(
                fontSize = 24.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(id = R.color.purple_500)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(start =8.dp, end = 8.dp)
        )
    }
}