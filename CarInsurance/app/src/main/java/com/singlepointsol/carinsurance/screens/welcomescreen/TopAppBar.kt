package com.singlepointsol.carinsurance.screens.welcomescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onOpenDrawer: () -> Unit
) {
    androidx.compose.material3.TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.6f)
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Bar",
                modifier = Modifier
                    .clickable { onOpenDrawer() }
                    .padding(start = 16.dp)
                    .size(28.dp)
            )
        },
        title = {
            Text(
                "ABZ Vehicle Insurance",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    )
}
