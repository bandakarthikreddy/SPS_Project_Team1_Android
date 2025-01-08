package com.singlepointsol.carinsurance.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldStyle() = TextStyle(
    fontSize = 12.sp,
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Normal,
    color = MaterialTheme.colorScheme.onSurface
)