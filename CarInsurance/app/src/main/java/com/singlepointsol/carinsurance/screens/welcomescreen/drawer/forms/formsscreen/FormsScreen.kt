package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.formsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.singlepointsol.carinsurance.R

@Composable
fun FormsScreen(modifier: Modifier = Modifier, navController: NavController) {

    val formItems = listOf(
        "Customer" to "customer",
        "Vehicle" to "vehicle",
        "Product" to "product",
        "Product Add On" to "productAddon",
        "Agent" to "agent",
        "Proposal" to "proposal",
        "Policy" to "policy",
        "Policy Add On" to "policyAddon",
        "Claims" to "claims",
        "Customer Query" to "customerQuery",
        "Query Response" to "queryResponse"

    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.custom))
            .windowInsetsPadding(WindowInsets.systemBars) // Apply padding below system bars
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), // Padding for the content inside LazyColumn
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Forms Page",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            // Dynamically create buttons for each form item
            items(formItems) { (label, route) ->
                Button(
                    onClick = { navController.navigate(route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Text(label)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}