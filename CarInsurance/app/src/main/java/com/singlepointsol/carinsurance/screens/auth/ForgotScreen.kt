package com.singlepointsol.carinsurance.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (email.value.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(email.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Reset link sent!", Toast.LENGTH_LONG).show()
                            navController.navigate("LOGIN")
                        } else {
                            Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Please enter your email", Toast.LENGTH_LONG).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("SUBMIT")
        }
    }
}
