package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.VehicleDataClassItem
import android.app.DatePickerDialog
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.singlepointsol.carinsurance.form.VehicleForm
import com.singlepointsol.carinsurance.viewmodel.VehicleViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclePage(modifier: Modifier, viewModel: VehicleViewModel) {

    val vehicleViewModel : VehicleViewModel = viewModel()
    val customerViewModel : CustomerViewModel = viewModel()

    var form by remember { mutableStateOf(VehicleForm()) }
    val vehicleData by vehicleViewModel.vehicleData.collectAsState()
    val context = LocalContext.current
    val isLoading by vehicleViewModel.isLoading.collectAsState()
    val vehicleList by vehicleViewModel.vehicleList.collectAsState()
    var expandedVehicleList by remember { mutableStateOf(false) }
    var selectedVehicleList by remember { mutableStateOf("") }

    val customerList by customerViewModel.customerList.collectAsState()
    var expandedCustomerID by remember { mutableStateOf(false) }
    var selectedCustomerID by remember { mutableStateOf("") }

    var expandedFuelType by remember { mutableStateOf(false) }
    var selectedFuelType by remember { mutableStateOf("") }

    // DatePicker related variables
    val calendar = Calendar.getInstance()
    var datePickerDialog: DatePickerDialog? = null

    LaunchedEffect(Unit) {
        customerViewModel.getCustomer(context)
        vehicleViewModel.getVehicle(context)
    }
    LaunchedEffect(vehicleData) {
        vehicleData?.let {
            form = form.copy(
                regNo = it.regNo,
                regAuthority = it.regAuthority,
                ownerId = it.ownerId,
                make = it.make,
                model = it.model,
                fuelType = it.fuelType,
                variant = it.variant,
                engineNo = it.engineNo,
                chassisNo = it.chassisNo,
                engineCapacity = it.engineCapacity,
                seatingCapacity = it.seatingCapacity,
                mfgYear = it.mfgYear,
                regDate = it.regDate,
                bodyType = it.bodyType,
                leasedBy = it.leasedBy
            )
        }
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
                    form = form.copy(regDate = formattedDate)
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
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                color = colorResource(id = R.color.black)
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {

                OutlinedTextField(
                    value = form.regNo,
                    onValueChange = { form = form.copy(regNo = it) },
                    label = { Text("Reg No") },
                    modifier = Modifier.fillMaxWidth()
                )

            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.regAuthority,
                    onValueChange = { form = form.copy(regAuthority = it) },
                    label = {
                        Text("Registration Authority", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters
                    )
                )

            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = expandedCustomerID,
                    onExpandedChange = { expandedCustomerID = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCustomerID,
                        onValueChange = { selectedCustomerID = it },
                        label = { Text("Owner ID", style = TextFieldStyle()) },
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
                                        form = form.copy(ownerId = customer.customerID)
                                        expandedCustomerID = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.make,
                    onValueChange = { form = form.copy(make = it) },
                    label = {
                        Text("Make", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.run { height(8.dp) })
            }
            item {
                OutlinedTextField(
                    value = form.model,
                    onValueChange = { form = form.copy(model = it) },
                    label = {
                        Text("Model", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            item {
                Spacer(modifier = Modifier.run { height(8.dp) })
            }
            item {
                ExposedDropdownMenuBox(
                    expanded = expandedFuelType,
                    onExpandedChange = { expandedFuelType = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedFuelType,
                        onValueChange = { selectedFuelType = it },
                        label = { Text("Fuel Type", style = TextFieldStyle()) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFuelType)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedFuelType,
                        onDismissRequest = { expandedFuelType = false }
                    ) {
                        listOf("P", "D", "C", "L", "E").forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(mode) },
                                onClick = {
                                    selectedFuelType = mode
                                    form = form.copy(fuelType = mode)
                                    expandedFuelType = false
                                }
                            )
                        }
                    }

                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.variant,
                    onValueChange = { form = form.copy(variant = it) },
                    label = {
                        Text("Variant", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.engineNo,
                    onValueChange = { form = form.copy(engineNo = it) },
                    label = {
                        Text("Engine Number", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.chassisNo,
                    onValueChange = { form = form.copy(chassisNo = it) },
                    label = {
                        Text("Chassis Number", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.engineCapacity,
                    onValueChange = { form = form.copy(engineCapacity = it) },
                    label = {
                        Text("Engine Capacity", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.seatingCapacity,
                    onValueChange = { form = form.copy(seatingCapacity = it) },
                    label = {
                        Text("Seating Capacity", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.mfgYear,
                    onValueChange = { form = form.copy(mfgYear = it) },
                    label = {
                        Text("Manufactured Year", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.regDate,
                    onValueChange = { form = form.copy(regNo = it) },
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
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.bodyType,
                    onValueChange = { form = form.copy(bodyType = it) },
                    label = {
                        Text("Body Type", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.leasedBy,
                    onValueChange = { form = form.copy(leasedBy = it) },
                    label = {
                        Text("Leased By", style = TextFieldStyle())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (form.regNo.isNotEmpty()) {
                                    val newVehicle = VehicleDataClassItem(
                                        regNo = form.regNo,
                                        regAuthority = form.regAuthority,
                                        ownerId = form.ownerId,
                                        make = form.make,
                                        model = form.model,
                                        fuelType = form.fuelType,
                                        variant = form.variant,
                                        engineNo = form.engineNo,
                                        chassisNo = form.chassisNo,
                                        engineCapacity = form.engineCapacity,
                                        seatingCapacity = form.seatingCapacity,
                                        mfgYear = form.mfgYear,
                                        regDate = form.regDate,
                                        bodyType = form.bodyType,
                                        leasedBy = form.leasedBy
                                    )
                                    vehicleViewModel.addVehicle(newVehicle, context)
                                    form = VehicleForm()
                                }
                            },
                            modifier = Modifier.weight(1f).padding(4.dp)
                        ) {
                            Text("ADD", style = ButtonTextFieldStyle())
                        }

                        Button(
                            onClick = {
                                if (form.regNo.isNotEmpty()) {
                                    vehicleViewModel.getVehicleByRegNo(form.regNo, context)
                                }
                            },
                            modifier = Modifier.weight(1f).padding(4.dp)
                        ) {
                            Text("FETCH", style = ButtonTextFieldStyle())
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (form.regNo.isNotEmpty()) {
                                    val updatedVehicle = VehicleDataClassItem(
                                        regNo = form.regNo,
                                        regAuthority = form.regAuthority,
                                        ownerId = form.ownerId,
                                        make = form.make,
                                        model = form.model,
                                        fuelType = form.fuelType,
                                        variant = form.variant,
                                        engineNo = form.engineNo,
                                        chassisNo = form.chassisNo,
                                        engineCapacity = form.engineCapacity,
                                        seatingCapacity = form.seatingCapacity,
                                        mfgYear = form.mfgYear,
                                        regDate = form.regDate,
                                        bodyType = form.bodyType,
                                        leasedBy = form.leasedBy
                                    )
                                    vehicleViewModel.updateVehicle(updatedVehicle, form.regNo, context)
                                }
                            },
                            modifier = Modifier.weight(1f).padding(4.dp)
                        ) {
                            Text("UPDATE", style = ButtonTextFieldStyle())
                        }

                        Button(
                            onClick = {
                                if (form.regNo.isNotEmpty()) {
                                    vehicleViewModel.deleteVehicle(form.regNo, context)
                                    form = VehicleForm()
                                }
                            },
                            modifier = Modifier.weight(1f).padding(4.dp)
                        ) {
                            Text("DELETE", style = ButtonTextFieldStyle())
                        }
                    }
                }
            }
        }
    }
}
