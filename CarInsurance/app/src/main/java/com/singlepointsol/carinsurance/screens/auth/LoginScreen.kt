package com.singlepointsol.carinsurance.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.navigation.Graph

@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(

                painter = painterResource(id = R.drawable.car_insurance),
                contentDescription = "Login Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible.value = !isPasswordVisible.value }) {
                        Icon(
                            imageVector = if (isPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (isPasswordVisible.value) "Hide Password" else "Show Password"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    navController.navigate("FORGOT")
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Forgot Password?")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(email.value, password.value)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()
                                    navController.navigate(Graph.MAIN_SCREEN_PAGE)
                                } else {
                                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Please enter email and password", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LOGIN")
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    navController.navigate("SIGNUP")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Don't have an account? Sign Up")
            }
        }
    }
}
