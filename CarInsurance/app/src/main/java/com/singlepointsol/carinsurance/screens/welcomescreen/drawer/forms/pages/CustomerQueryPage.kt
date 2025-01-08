package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.dataclass.CustomerQueryDataClassItem
import com.singlepointsol.carinsurance.form.CustomerQueryForm
import com.singlepointsol.carinsurance.viewmodel.CustomerQueryViewModel
import com.singlepointsol.carinsurance.viewmodel.CustomerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.components.TextFieldStyle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerQueryPage(modifier: Modifier, viewModel: CustomerQueryViewModel) {

    val customerQueryViewModel: CustomerQueryViewModel = viewModel()
    val customerViewModel: CustomerViewModel = viewModel()

    var form by remember { mutableStateOf(CustomerQueryForm()) }
    val customerQueryData by customerQueryViewModel.customerQueryData.collectAsState()
    val context = LocalContext.current

    val customerQueryList by customerQueryViewModel.customerQueryList.collectAsState()
    var expandedCustomerQuery by remember { mutableStateOf(false) }
    var selectedCustomerQuery by remember { mutableStateOf("") }
    val isLoading by customerQueryViewModel.isLoading.collectAsState()
    val customerList by customerViewModel.customerList.collectAsState()
    var expandedCustomerID by remember { mutableStateOf(false) }
    var selectedCustomerID by remember { mutableStateOf("") }

    // DatePicker related variables
    val calendar = Calendar.getInstance()
    var datePickerDialog: DatePickerDialog? = null

    // Moving LaunchedEffect properly within composable scope:
    LaunchedEffect(Unit) {
        customerViewModel.getCustomer(context)
        customerQueryViewModel.getCustomerQuery(context)
    }

    // Update form when customerQueryData changes
    LaunchedEffect(customerQueryData) {
        customerQueryData?.let {
            form = form.copy(
                queryID = it.queryID,
                customerID = it.customerID,
                description = it.description,
                queryDate = it.queryDate,
                status = it.status
            )
        }
    }

    // Log updates when customer lists change
    LaunchedEffect(customerList) {
        Log.d("CustomerPage", "Customer List: $customerList")
    }

    LaunchedEffect(customerQueryList) {
        Log.d("CustomerQueryPage", "Customer Query List: $customerQueryList")
    }

    fun showDatePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    // Format selected date as "2025-01-02T02:45:48.499Z"
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val formattedDate = formatter.format(selectedDate.time)
                    form = form.copy(queryDate = formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog?.show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        // Loading indicator
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                color = colorResource(id = R.color.black)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {

                OutlinedTextField(
                    value = form.queryID,
                    onValueChange = { form = form.copy(queryID = it) },
                    label = { Text("Query ID") },
                    modifier = Modifier.fillMaxWidth()
                )


            }

            item {
                val filteredCustomerList = customerList.filter { it.customerID == selectedCustomerID }
                ExposedDropdownMenuBox(
                    expanded = expandedCustomerID,
                    onExpandedChange = { expandedCustomerID = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCustomerID,
                        onValueChange = { selectedCustomerID = it },
                        label = { Text("Customer ID", style = TextFieldStyle()) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCustomerID)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    if (customerList.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = expandedCustomerID,
                            onDismissRequest = { expandedCustomerID = false }
                        ) {
                            customerList.forEach { customer ->
                                DropdownMenuItem(
                                    text = { Text(customer.customerID) },
                                    onClick = {
                                        selectedCustomerID = customer.customerID
                                        form = form.copy(customerID = customer.customerID)
                                        expandedCustomerID = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = form.description,
            onValueChange = { form = form.copy(description = it) },
            label = {
                Text("Description", style = TextFieldStyle())
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = form.queryDate,
            onValueChange = { },
            label = { Text("Registration Date", style = TextFieldStyle()) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = false,
            trailingIcon = {
                IconButton(onClick = { showDatePicker() }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange, // This is the Date Range Icon from Material Design
                        contentDescription = "Pick Date"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = form.status,
            onValueChange = { form = form.copy(status = it) },
            label = {
                Text("Status", style = TextFieldStyle())
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Spacer to ensure proper alignment
        Spacer(modifier = Modifier.height(16.dp))

        // Button Row for Add, Fetch, Update, Delete actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between buttons
        ) {
            // Left column buttons (Add, Fetch)
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (form.queryID.isNotEmpty()) {
                            val addCustomerQuery = CustomerQueryDataClassItem(
                                queryID = form.queryID,
                                customerID = form.customerID,
                                description = form.description,
                                queryDate = form.queryDate,
                                status = form.status
                            )
                            customerQueryViewModel.addCustomerQuery(form.queryID, addCustomerQuery,  context)
                            form = CustomerQueryForm()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("ADD", style = ButtonTextFieldStyle())
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (form.queryID.isNotEmpty()) {
                            customerQueryViewModel.getCustomerQueryById(form.queryID, context)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("FETCH", style = ButtonTextFieldStyle())
                }
            }

            // Right column buttons (Update, Delete)
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (form.queryID.isNotEmpty()) {
                            val updatedCustomerQuery = CustomerQueryDataClassItem(
                                queryID = form.queryID,
                                customerID = form.customerID,
                                description = form.description,
                                queryDate = form.queryDate,
                                status = form.status
                            )
                            customerQueryViewModel.updateCustomerQuery(updatedCustomerQuery, form.queryID, context)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("UPDATE", style = ButtonTextFieldStyle())
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (form.queryID.isNotEmpty()) {
                            customerQueryViewModel.deleteCustomerQuery(form.queryID, context)
                            form = CustomerQueryForm()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("DELETE", style = ButtonTextFieldStyle())
                }
            }
        }
    }
}
