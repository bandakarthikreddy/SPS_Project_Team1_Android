package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.CustomerDataClassItem
import com.singlepointsol.carinsurance.form.CustomerForm
import com.singlepointsol.carinsurance.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerPage(
    modifier: Modifier = Modifier,
    viewModel: CustomerViewModel
) {

    val customerViewModel: CustomerViewModel = viewModel()

    var form by remember { mutableStateOf(CustomerForm()) }
    val customerData by customerViewModel.customerData.collectAsState()
    val isLoading by customerViewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val customerList by customerViewModel.customerList.collectAsState()
    var expandedCustomerID by remember { mutableStateOf(false) }
    var selectedCustomerID by remember { mutableStateOf("") }

    var isPhoneError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        customerViewModel.getCustomer(context)
    }

    LaunchedEffect(customerList) {
        Log.d("CustomerPage", "Customer List: $customerList")
    }

    LaunchedEffect(customerData) {
        Log.d("CustomerPage", "Customer Data Updated: $customerData")
        customerData?.let {
            form = form.copy(
                customerID = it.customerID,
                customerName = it.customerName,
                customerPhone = it.customerPhone,
                customerEmail = it.customerEmail,
                customerAddress = it.customerAddress
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.systemBars),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                color = colorResource(id = R.color.black)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                item {
            OutlinedTextField(
                value = form.customerID,
                onValueChange = { form = form.copy(customerID = it) },
                label = { Text("Customer ID") },
                modifier = Modifier.fillMaxWidth()
            )
            }

            item {
                OutlinedTextField(
                    value = form.customerName,
                    onValueChange = { form = form.copy(customerName = it) },
                    label = { Text("Customer Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = form.customerPhone,
                    onValueChange = {
                        if (it.length <= 10) {
                            form = form.copy(customerPhone = it)
                            isPhoneError = it.length != 10
                        }
                    },
                    label = { Text("Phone") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = isPhoneError,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (isPhoneError) {
                    Text(
                        text = "Phone number must be exactly 10 digits.",
                        color = colorResource(id = R.color.black),
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }
            item {
                OutlinedTextField(
                    value = form.customerEmail,
                    onValueChange = { form = form.copy(customerEmail = it) },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = form.customerAddress,
                    onValueChange = { form = form.copy(customerAddress = it) },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        val newCustomer = CustomerDataClassItem(
                            customerID = form.customerID,
                            customerName = form.customerName,
                            customerPhone = form.customerPhone,
                            customerEmail = form.customerEmail,
                            customerAddress = form.customerAddress
                        )
                        customerViewModel.addNewCustomer(form.customerID, newCustomer, context)
                        form = CustomerForm()
                    },
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 8.dp)
                ) {
                    Text("ADD")
                }

                Button(
                    onClick = {
                        if (form.customerID.isNotEmpty()) {
                            Log.d("CustomerPage", "Fetching Customer ID: ${form.customerID}")
                            customerViewModel.getCustomerById(form.customerID, context)
                        }
                    },
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 8.dp)
                ) {
                    Text("FETCH")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (form.customerID.isNotEmpty()) {
                            val updatedCustomer = CustomerDataClassItem(
                                customerID = form.customerID,
                                customerName = form.customerName,
                                customerPhone = form.customerPhone,
                                customerEmail = form.customerEmail,
                                customerAddress = form.customerAddress
                            )
                            customerViewModel.updateCustomerData(updatedCustomer, context)
                        }
                    },
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 8.dp)
                ) {
                    Text("UPDATE")
                }

                Button(
                    onClick = {
                        if (form.customerID.isNotEmpty()) {
                            Log.d("CustomerPage", "Fetching Customer ID: ${form.customerID}")
                            customerViewModel.deleteCustomerData(form.customerID, context)
                            form = CustomerForm()
                        }
                    },
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 8.dp)
                ) {
                    Text("DELETE")
                }
            }
        }
    }
}
