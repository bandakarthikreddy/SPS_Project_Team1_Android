package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.singlepointsol.carinsurance.dataclass.ProposalDataClassItem
import com.singlepointsol.carinsurance.form.ProposalForm
import com.singlepointsol.carinsurance.viewmodel.CustomerViewModel
import com.singlepointsol.carinsurance.viewmodel.ProductViewModel
import com.singlepointsol.carinsurance.viewmodel.ProposalViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProposalPage(modifier: Modifier, viewModel: ProposalViewModel) {

    val proposalViewModel: ProposalViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()
    val customerViewModel: CustomerViewModel = viewModel()

    var form by remember { mutableStateOf(ProposalForm()) }
    val proposalData by proposalViewModel.proposalData.collectAsState()
    val context = LocalContext.current
    val isLoading by proposalViewModel.isLoading.collectAsState()
    val proposalList by proposalViewModel.proposalList.collectAsState()
    var expandedProposalNo by remember { mutableStateOf(false) }
    var selectedProposalNo by remember { mutableStateOf("") }
    val productList by productViewModel.productList.collectAsState()
    var expandedProductID by remember { mutableStateOf(false) }
    var selectedProductID by remember { mutableStateOf("") }
    val customerList by customerViewModel.customerList.collectAsState()
    var expandedCustomerID by remember { mutableStateOf(false) }
    var selectedCustomerID by remember { mutableStateOf("") }

    // DatePicker related variables
    val calendar = Calendar.getInstance()
    var datePickerDialog: DatePickerDialog? = null

    LaunchedEffect(Unit) {
        customerViewModel.getCustomer(context)
        productViewModel.getProduct(context)
        proposalViewModel.getProposal(context)
    }

    LaunchedEffect(customerList) {
        Log.d("CustomerPage", "Customer List: $customerList")
        Log.d("ProductPage", "Product List: $productList")
        Log.d("ProposalPage", "Proposal List: $proposalList")
    }

    LaunchedEffect(proposalData) {
        proposalData?.let {
            form = form.copy(
                proposalNo = it.proposalNo,
                regNo = it.regNo,
                productID = it.productID,
                customerID = it.customerID,
                fromDate = it.fromDate,
                toDate = it.toDate,
                idv = it.idv,
                agentID = it.agentID,
                basicAmount = it.basicAmount,
                totalAmount = it.totalAmount
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
                    form = form.copy(fromDate = formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog?.show()
        }
    }

    fun showDatePicker1() {
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
                    form = form.copy(toDate = formattedDate)
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
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                color = colorResource(id = R.color.black)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {

                OutlinedTextField(
                    value = form.proposalNo,
                    onValueChange = { form = form.copy(proposalNo = it) },
                    label = { Text("Proposal No") },
                    modifier = Modifier.fillMaxWidth()

                )

            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.regNo,
                    onValueChange = { form = form.copy(regNo = it) },
                    label = { Text("Registration Number", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))


                OutlinedTextField(
                    value = form.productID,
                    onValueChange = { form = form.copy(productID = it) },
                    label = {
                        Text("Product ID")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
             OutlinedTextField(
                 value = form.customerID,
                 onValueChange = { form = form.copy(customerID = it)},
                 label = {Text("Customer ID")},
                 modifier = Modifier.fillMaxWidth()
             )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.fromDate,
                    onValueChange = { form = form.copy(fromDate = it) },
                    label = { Text("From Date", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = false,
                    trailingIcon = {
                        androidx.compose.material.IconButton(onClick = { showDatePicker() }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Filled.DateRange, // This is the Date Range Icon from Material Design
                                contentDescription = "Pick Date"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.toDate,
                    onValueChange = { form = form.copy(toDate = it) },
                    label = { Text("To Date", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = false,
                    trailingIcon = {
                        androidx.compose.material.IconButton(onClick = { showDatePicker1() }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Filled.DateRange, // This is the Date Range Icon from Material Design
                                contentDescription = "Pick Date"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.idv,
                    onValueChange = { form = form.copy(idv = it) },
                    label = { Text("IDV", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.agentID,
                    onValueChange = { form = form.copy(agentID = it) },
                    label = { Text("Agent ID", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.basicAmount,
                    onValueChange = { form = form.copy(basicAmount = it) },
                    label = { Text("Basic Amount", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.totalAmount,
                    onValueChange = { form = form.copy(totalAmount = it) },
                    label = { Text("Total Amount", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
            // Updated buttons in two columns and two rows
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            val newProposal = ProposalDataClassItem(
                                proposalNo = form.proposalNo,
                                regNo = form.regNo,
                                productID = form.productID,
                                customerID = form.customerID,
                                fromDate = form.fromDate,
                                toDate = form.toDate,
                                idv = form.idv,
                                agentID = form.agentID,
                                basicAmount = form.basicAmount,
                                totalAmount = form.totalAmount
                            )
                            proposalViewModel.addProposal(form.proposalNo, newProposal, context)
                            form = ProposalForm()
                        },
                        modifier = Modifier.weight(1f).padding(4.dp)
                    ) {
                        Text("ADD", style = ButtonTextFieldStyle())
                    }

                    Button(
                        onClick = {
                            if (form.proposalNo.isNotEmpty()) {
                                proposalViewModel.getProposalByID(form.proposalNo, context)
                            }
                        },
                        modifier = Modifier.weight(1f).padding(4.dp)
                    ) {
                        Text("FETCH", style = ButtonTextFieldStyle())
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            if (form.proposalNo.isNotEmpty()) {
                                val updatedProposal = ProposalDataClassItem(
                                    proposalNo = form.proposalNo,
                                    regNo = form.regNo,
                                    productID = form.productID,
                                    customerID = form.customerID,
                                    fromDate = form.fromDate,
                                    toDate = form.toDate,
                                    idv = form.idv,
                                    agentID = form.agentID,
                                    basicAmount = form.basicAmount,
                                    totalAmount = form.totalAmount
                                )
                                proposalViewModel.updateProposal(updatedProposal, form.proposalNo, context)
                            }
                        },
                        modifier = Modifier.weight(1f).padding(4.dp)
                    ) {
                        Text("UPDATE", style = ButtonTextFieldStyle())
                    }

                    Button(
                        onClick = {
                            if (form.proposalNo.isNotEmpty()) {
                                proposalViewModel.deleteProposal(form.proposalNo, context)
                                form = ProposalForm()
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
