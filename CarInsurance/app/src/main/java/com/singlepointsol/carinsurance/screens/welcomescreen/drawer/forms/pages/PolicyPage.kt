package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import android.app.DatePickerDialog
import android.os.Build
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.singlepointsol.carinsurance.form.PolicyForm
import com.singlepointsol.carinsurance.viewmodel.PolicyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.PolicyDataClassItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyPage(modifier: Modifier = Modifier, viewModel: PolicyViewModel) {
    
    val policyViewModel: PolicyViewModel = viewModel()
    
    var form by remember { mutableStateOf(PolicyForm()) }
    val policyData by policyViewModel.policyData.collectAsState()
    val context = LocalContext.current
    val isLoading by policyViewModel.isLoading.collectAsState()
    val policyList by policyViewModel.policyList.collectAsState()
    var expandedPolicyNo by remember { mutableStateOf(false) }
    var selectedPolicyNo by remember { mutableStateOf("") }
    var expandedPaymentMode by remember { mutableStateOf(false) }

    // DatePicker related variables
    val calendar = Calendar.getInstance()
    var datePickerDialog: DatePickerDialog? = null

    LaunchedEffect(Unit) {
        policyViewModel.getPolicy(context)
    }

    LaunchedEffect(policyData) {
        policyData?.let {
            form = form.copy(
                policyNo = it.policyNo,
                proposalNo = it.proposalNo,
                noClaimBonus = it.noClaimBonus,
                receiptNo = it.receiptNo,
                receiptDate = it.receiptDate,
                paymentMode = it.paymentMode,
                amount = it.amount
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
                    form = form.copy(receiptDate = formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog?.show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                color = colorResource(id = R.color.black)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                OutlinedTextField(
                    value = form.policyNo,
                    onValueChange = { form = form.copy(policyNo = it) },
                    label = { Text("Policy No") },
                    modifier = Modifier.fillMaxWidth()
                )

            }

            item {
                OutlinedTextField(
                    value = form.proposalNo,
                    onValueChange = { form = form.copy(proposalNo = it) },
                    label = { Text("Proposal Number", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = form.noClaimBonus,
                    onValueChange = { form = form.copy(noClaimBonus = it) },
                    label = { Text("No Claim Bonus", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = form.receiptNo,
                    onValueChange = { form = form.copy(receiptNo = it) },
                    label = { Text("Receipt Number", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = form.receiptDate,
                    onValueChange = { },
                    label = { Text("Receipt Date", style = TextFieldStyle()) },
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

            // Dropdown with manual fields for Payment Mode
            item {
                ExposedDropdownMenuBox(
                    expanded = expandedPaymentMode,
                    onExpandedChange = { expandedPaymentMode = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = form.paymentMode,
                        onValueChange = { form = form.copy(paymentMode = it) },
                        label = { Text("Payment Mode", style = TextFieldStyle()) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPaymentMode)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedPaymentMode,
                        onDismissRequest = { expandedPaymentMode = false }
                    ) {
                        listOf("C", "Q", "U", "D").forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(mode) },
                                onClick = {
                                    form = form.copy(paymentMode = mode)
                                    expandedPaymentMode = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = form.amount,
                    onValueChange = { form = form.copy(amount = it) },
                    label = { Text("Amount", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Spacer added for amount field
            item {
                Spacer(modifier = Modifier.height(16.dp))  // Added spacer for better spacing
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (form.policyNo.isNotEmpty()) {
                                    val newPolicy = PolicyDataClassItem(
                                        policyNo = form.policyNo,
                                        proposalNo = form.proposalNo,
                                        noClaimBonus = form.noClaimBonus,
                                        receiptNo = form.receiptNo,
                                        receiptDate = form.receiptDate,
                                        paymentMode = form.paymentMode,
                                        amount = form.amount
                                    )
                                    policyViewModel.addPolicy(form.policyNo, newPolicy, context)
                                    form = PolicyForm()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("ADD", style = ButtonTextFieldStyle())
                        }

                        Button(
                            onClick = {
                                if (form.policyNo.isNotEmpty()) {
                                    policyViewModel.getPolicyByPolicyNo(form.policyNo, context)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("FETCH", style = ButtonTextFieldStyle())
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (form.policyNo.isNotEmpty()) {
                                    val updatePolicy = PolicyDataClassItem(
                                        policyNo = form.policyNo,
                                        proposalNo = form.proposalNo,
                                        noClaimBonus = form.noClaimBonus,
                                        receiptNo = form.receiptNo,
                                        receiptDate = form.receiptDate,
                                        paymentMode = form.paymentMode,
                                        amount = form.amount
                                    )
                                    policyViewModel.updatePolicy(updatePolicy, form.policyNo, context)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("UPDATE", style = ButtonTextFieldStyle())
                        }

                        Button(
                            onClick = {
                                if (form.policyNo.isNotEmpty()) {
                                    policyViewModel.deletePolicy(form.policyNo, context)
                                    form = PolicyForm()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("DELETE", style = ButtonTextFieldStyle())
                        }
                    }
                }
            }
        }
    }
}
