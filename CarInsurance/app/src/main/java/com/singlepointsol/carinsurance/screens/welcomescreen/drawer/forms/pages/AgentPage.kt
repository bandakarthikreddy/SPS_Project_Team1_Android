package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.singlepointsol.carinsurance.form.AgentForm
import com.singlepointsol.carinsurance.viewmodel.AgentViewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.dataclass.AgentDataClassItem
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentPage(modifier: Modifier = Modifier, viewModel: AgentViewModel = viewModel()) {

    val agentViewModel:AgentViewModel = viewModel()
    // ViewModel and States
    var form by remember { mutableStateOf(AgentForm()) }
    val agentData by agentViewModel.agentData.collectAsState()
    val agentList by viewModel.agentList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    // Dropdown menu state
    var expandedAgentID by remember { mutableStateOf(false) }
    var selectedAgentID by remember { mutableStateOf("") }

    // Effects
    LaunchedEffect(Unit) {
        viewModel.getAgent(context)
    }

    LaunchedEffect(agentList) {
        Log.d("AgentPage", "Agent List: $agentList")
    }

    LaunchedEffect(agentData) {
        agentData?.let {
            form = form.copy(
                agentID = it.agentID,
                agentName = it.agentName,
                agentPhone = it.agentPhone,
                agentEmail = it.agentEmail,
                licenseCode = it.licenseCode
            )
        }
    }

    // UI Content
    Column(
        modifier = modifier
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

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                OutlinedTextField(
                    value = form.agentID,
                    onValueChange = { form = form.copy(agentID = it) },
                    label = { Text("Agent ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.agentName,
                    onValueChange = { form = form.copy(agentName = it) },
                    label = { Text("Agent Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.agentPhone,
                    onValueChange = { form = form.copy(agentPhone = it) },
                    label = { Text("Agent Phone") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.agentEmail,
                    onValueChange = { form = form.copy(agentEmail = it) },
                    label = { Text("Agent Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.licenseCode,
                    onValueChange = { form = form.copy(licenseCode = it) },
                    label = { Text("License Code") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = {
                        if (form.agentID.isNotEmpty()) {
                            val addAgent = AgentDataClassItem(
                                agentID = form.agentID,
                                agentName = form.agentName,
                                agentPhone = form.agentPhone,
                                agentEmail = form.agentEmail,
                                licenseCode = form.licenseCode
                            )
                            agentViewModel.addAgent(form.agentID, addAgent, context)
                            form = AgentForm()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Text("ADD")
                }

                Button(
                    onClick = {
                        if (form.agentID.isNotEmpty()) {
                            agentViewModel.getAgentByAgentID(form.agentID, context)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Text("FETCH")
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = {
                        if (form.agentID.isNotEmpty()) {
                            val updatedAgent = AgentDataClassItem(
                                agentID = form.agentID,
                                agentName = form.agentName,
                                agentPhone = form.agentPhone,
                                agentEmail = form.agentEmail,
                                licenseCode = form.licenseCode
                            )
                            agentViewModel.updateAgent(updatedAgent, form.agentID, context)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Text("UPDATE")
                }

                Button(
                    onClick = {
                        if (form.agentID.isNotEmpty()) {
                            agentViewModel.deleteAgent(form.agentID, context)
                            form = AgentForm()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Text("DELETE")
                }
            }
        }
    }
}
