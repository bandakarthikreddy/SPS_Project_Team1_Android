package com.singlepointsol.carinsurance.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
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

@Composable
fun SignupScreen(navController: NavController) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val isPasswordVisible = remember { mutableStateOf(false) }
    val isConfirmPasswordVisible = remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.car),
            contentDescription = "SignUp Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = {
                if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                    phoneNumber.value = it
                }
            },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

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

        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            label = { Text("Confirm Password") },
            visualTransformation = if (isConfirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible.value = !isConfirmPasswordVisible.value }) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isConfirmPasswordVisible.value) "Hide Password" else "Show Password"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (firstName.value.isNotEmpty() && lastName.value.isNotEmpty() &&
                email.value.isNotEmpty() && password.value.isNotEmpty() &&
                confirmPassword.value.isNotEmpty() && phoneNumber.value.isNotEmpty()) {

                if (phoneNumber.value.length != 10) {
                    Toast.makeText(context, "Phone number must be exactly 10 digits", Toast.LENGTH_LONG).show()
                } else if (password.value != confirmPassword.value) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                } else {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Sign-Up Successful!", Toast.LENGTH_LONG).show()
                                navController.navigate("LOGIN")
                            } else {
                                Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("SIGN UP")
        }
    }
}
