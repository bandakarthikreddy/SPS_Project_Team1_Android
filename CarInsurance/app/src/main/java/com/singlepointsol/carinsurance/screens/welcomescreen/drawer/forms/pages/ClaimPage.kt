package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.ClaimDataClassItem
import com.singlepointsol.carinsurance.form.ClaimForm
import com.singlepointsol.carinsurance.viewmodel.ClaimViewModel
import com.singlepointsol.carinsurance.viewmodel.PolicyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClaimPage(modifier: Modifier, viewModel: ClaimViewModel) {

    val claimViewModel: ClaimViewModel = viewModel()
    val policyViewModel: PolicyViewModel = viewModel()

    val calendar = Calendar.getInstance()
    var datePickerDialog: DatePickerDialog? = null

    var form by remember { mutableStateOf(ClaimForm()) }
    val claimData by claimViewModel.claimData.collectAsState()
    val context = LocalContext.current
    val policyList by policyViewModel.policyList.collectAsState()
    val isLoading by claimViewModel.isLoading.collectAsState()
    val claimList by claimViewModel.claimList.collectAsState()
    var expandedPolicyNo by remember { mutableStateOf(false) }
    var selectedPolicyNo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        policyViewModel.getPolicy(context)
        claimViewModel.getClaim(context)
    }

    LaunchedEffect(claimData) {
        claimData?.let {
            form = form.copy(
                claimNo = it.claimNo,
                claimDate = it.claimDate,
                policyNo = it.policyNo,
                incidentDate = it.incidentDate,
                incidentLocation = it.incidentLocation,
                incidentDescription = it.incidentDescription,
                claimAmount = it.claimAmount,
                surveyorName = it.surveyorName,
                surveyorPhone = it.surveyorPhone,
                surveyorDate = it.surveyDate,
                surveyorDescription = it.surveyDescription,
                claimStatus = it.claimStatus
            )
        }
    }

    fun showDatePicker(onDateSelected: (String) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    onDateSelected(formatter.format(selectedDate.time))
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

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = colorResource(id = R.color.black)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                OutlinedTextField(
                    value = form.claimNo,
                    onValueChange = { form = form.copy(claimNo = it) },
                    label = { Text("Claim No") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.claimDate,
                    onValueChange = {},
                    label = { Text("Claim Date", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker { form = form.copy(claimDate = it) } }) {
                            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Pick Date")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedPolicyNo,
                    onExpandedChange = { expandedPolicyNo = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedPolicyNo,
                        onValueChange = { selectedPolicyNo = it },
                        label = { Text("Policy Number", style = TextFieldStyle()) },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPolicyNo)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    if (policyList.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = expandedPolicyNo,
                            onDismissRequest = { expandedPolicyNo = false }
                        ) {
                            policyList.forEach { policy ->
                                DropdownMenuItem(
                                    text = { Text(policy.policyNo) },
                                    onClick = {
                                        selectedPolicyNo = policy.policyNo
                                        form = form.copy(policyNo = policy.policyNo)
                                        expandedPolicyNo = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.incidentDate,
                    onValueChange = {},
                    label = { Text("Incident Date", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker { form = form.copy(incidentDate = it) } }) {
                            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Pick Date")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.incidentLocation,
                    onValueChange = { form = form.copy(incidentLocation = it) },
                    label = { Text("Incident Location", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Additional fields follow the same structure
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (form.claimNo.isNotEmpty()) {
                            val newClaim = ClaimDataClassItem(
                                claimNo = form.claimNo,
                                claimDate = form.claimDate,
                                policyNo = form.policyNo,
                                incidentDate = form.incidentDate,
                                incidentLocation = form.incidentLocation,
                                incidentDescription = form.incidentDescription,
                                claimAmount = form.claimAmount,
                                surveyorName = form.surveyorName,
                                surveyorPhone = form.surveyorPhone,
                                surveyDate = form.surveyorDate,
                                surveyDescription = form.surveyorDescription,
                                claimStatus = form.claimStatus
                            )
                            claimViewModel.addClaim(form.claimNo, newClaim, context)
                            form = ClaimForm()
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("ADD", style = ButtonTextFieldStyle())
                }

                Button(
                    onClick = {
                        if (form.claimNo.isNotEmpty()) {
                            claimViewModel.getClaimByClaimNo(form.claimNo, context)
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("FETCH", style = ButtonTextFieldStyle())
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (form.claimNo.isNotEmpty()) {
                            val updatedClaim = ClaimDataClassItem(
                                claimNo = form.claimNo,
                                claimDate = form.claimDate,
                                policyNo = form.policyNo,
                                incidentDate = form.incidentDate,
                                incidentLocation = form.incidentLocation,
                                incidentDescription = form.incidentDescription,
                                claimAmount = form.claimAmount,
                                surveyorName = form.surveyorName,
                                surveyorPhone = form.surveyorPhone,
                                surveyDate = form.surveyorDate,
                                surveyDescription = form.surveyorDescription,
                                claimStatus = form.claimStatus
                            )
                            claimViewModel.updateClaim(updatedClaim, form.claimNo, context)
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("UPDATE", style = ButtonTextFieldStyle())
                }

                Button(
                    onClick = {
                        if (form.claimNo.isNotEmpty()) {
                            claimViewModel.deleteClaim(form.claimNo, context)
                            form = ClaimForm()
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    Text("DELETE", style = ButtonTextFieldStyle())
                }
            }
        }
    }
}
