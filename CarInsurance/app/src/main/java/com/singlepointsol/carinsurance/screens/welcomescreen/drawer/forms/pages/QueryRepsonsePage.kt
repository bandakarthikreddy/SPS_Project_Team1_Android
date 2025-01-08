package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.QueryResponseDataClassItem
import com.singlepointsol.carinsurance.form.QueryResponseForm
import com.singlepointsol.carinsurance.form.VehicleForm
import com.singlepointsol.carinsurance.viewmodel.AgentViewModel
import com.singlepointsol.carinsurance.viewmodel.CustomerQueryViewModel
import com.singlepointsol.carinsurance.viewmodel.QueryResponseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueryResponsePage(modifier: Modifier, viewModel: QueryResponseViewModel) {
    val queryResponseViewModel: QueryResponseViewModel = viewModel()
    val customerQueryViewModel: CustomerQueryViewModel = viewModel()
    val agentViewModel: AgentViewModel = viewModel()
    var form by remember { mutableStateOf(QueryResponseForm()) }
    val queryResponseData by queryResponseViewModel.queryResponseData.collectAsState()
    val context = LocalContext.current
    val isLoading by queryResponseViewModel.isLoading.collectAsState()

    val queryResponseList by queryResponseViewModel.queryResponseList.collectAsState()
    var expandedQueryResponseList by remember { mutableStateOf(false) }
    var selectedQueryResponseList by remember { mutableStateOf("") }

    val customerQueryList by customerQueryViewModel.customerQueryList.collectAsState()
    var expandedCustomerQuery by remember { mutableStateOf(false) }
    var selectedCustomerQuery by remember { mutableStateOf("") }

    val agentList by agentViewModel.agentList.collectAsState()
    var expandedAgent by remember { mutableStateOf(false) }
    var selectedAgent by remember { mutableStateOf("") }

    // DatePicker related variables
    val calendar = Calendar.getInstance()
    var datePickerDialog: DatePickerDialog? = null

    LaunchedEffect(Unit) {
        queryResponseViewModel.getQueryResponse(context)
        customerQueryViewModel.getCustomerQuery(context)
        agentViewModel.getAgent(context)
    }

    LaunchedEffect(queryResponseData) {
        queryResponseData?.let {
            form = form.copy(
                queryID = it.queryID,
                srNo = it.srNo,
                agentID = it.agentID,
                description = it.description,
                responseDate = it.responseDate
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
                    val formatter =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val formattedDate = formatter.format(selectedDate.time)
                    form = form.copy(responseDate = formattedDate)
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {

                OutlinedTextField(
                    value = form.srNo,
                    onValueChange = { form = form.copy(srNo = it) },
                    label = { Text("Sr No") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.queryID,
                    onValueChange = { form = form.copy(queryID = it)},
                    label = {Text("Query ID")},
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = expandedAgent,
                    onExpandedChange = { expandedAgent = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedAgent,
                        onValueChange = { selectedAgent = it },
                        label = { Text("Agent ID", style = TextFieldStyle()) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAgent)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    if (agentList.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = expandedAgent,
                            onDismissRequest = { expandedAgent = false }
                        ) {
                            agentList.forEach { agent ->
                                DropdownMenuItem(
                                    text = { Text(agent.agentID) },
                                    onClick = {
                                        selectedAgent = agent.agentID
                                        form = form.copy(agentID = agent.agentID)
                                        expandedAgent = false
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
                    value = form.description,
                    onValueChange = { form = form.copy(description = it) },
                    label = {
                        Text("Description", style = TextFieldStyle())
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
                    value = form.responseDate,
                    onValueChange = { },
                    label = { Text("Response Date", style = TextFieldStyle()) },
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
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Button(
                        onClick = {
                            if (form.queryID.isNotEmpty()) {
                                val addCustomerQuery = QueryResponseDataClassItem(
                                    srNo = form.srNo,
                                    queryID = form.queryID,
                                    agentID = form.agentID,
                                    description = form.description,
                                    responseDate = form.responseDate
                                )
                                queryResponseViewModel.addQueryResponse(addCustomerQuery, context)
                                form = QueryResponseForm()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxWidth() // Ensures the button takes up equal space
                    ) {
                        Text("ADD", style = ButtonTextFieldStyle())
                    }
                }

                item {
                    Button(
                        onClick = {
                            if (form.queryID.isNotEmpty()) {
                                queryResponseViewModel.getQueryResponseById(
                                    form.queryID,
                                    form.srNo,
                                    context
                                )
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxWidth() // Ensures the button takes up equal space
                    ) {
                        Text("FETCH", style = ButtonTextFieldStyle())
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Button(
                        onClick = {
                            if (form.queryID.isNotEmpty()) {
                                val updatedCustomerQuery = QueryResponseDataClassItem(
                                    srNo = form.srNo,
                                    queryID = form.queryID,
                                    agentID = form.agentID,
                                    description = form.description,
                                    responseDate = form.responseDate
                                )
                                queryResponseViewModel.updateQueryResponse(
                                    form.queryID,
                                    form.srNo,
                                    updatedCustomerQuery,
                                    context
                                )
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxWidth() // Ensures the button takes up equal space
                    ) {
                        Text("UPDATE", style = ButtonTextFieldStyle())
                    }
                }

                item {
                    Button(
                        onClick = {
                            if (form.queryID.isNotEmpty()) {
                                queryResponseViewModel.deleteQueryResponse(
                                    form.queryID,
                                    form.srNo,
                                    context
                                )
                                form = QueryResponseForm()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxWidth() // Ensures the button takes up equal space
                    ) {
                        Text("DELETE", style = ButtonTextFieldStyle())
                    }
                }
            }
        }
    }
}