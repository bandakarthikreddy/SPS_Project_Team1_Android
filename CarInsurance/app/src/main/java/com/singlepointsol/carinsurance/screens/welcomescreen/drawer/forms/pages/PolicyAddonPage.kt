package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.PolicyAddonDataClassItem
import com.singlepointsol.carinsurance.form.PolicyAddonForm
import com.singlepointsol.carinsurance.viewmodel.PolicyAddonViewModel
import com.singlepointsol.carinsurance.viewmodel.PolicyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.form.ProductAddonForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyAddOnPage(modifier: Modifier = Modifier, viewModel: PolicyAddonViewModel) {

    val policyAddonViewModel: PolicyAddonViewModel = viewModel()
    val policyViewModel: PolicyViewModel = viewModel()
    var form by remember { mutableStateOf(PolicyAddonForm()) }
    val policyAddOnData by policyAddonViewModel.policyAddonData.collectAsState()
    val context = LocalContext.current

    val policyAddonList by policyAddonViewModel.policyAddonList.collectAsState()
    var expandedPolicyAddonID by remember { mutableStateOf(false) }
    var selectedPolicyAddonID by remember { mutableStateOf("") }
    val isLoading by policyAddonViewModel.isLoading.collectAsState()
    val policyList by policyViewModel.policyList.collectAsState()
    var expandedPolicyNo by remember { mutableStateOf(false) }
    var selectedPolicyNo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        policyViewModel.getPolicy(context)
        policyAddonViewModel.getPolicyAddon(context)
    }

    LaunchedEffect(policyAddOnData) {
        policyAddOnData?.let {
            form = form.copy(
                addonID = it.addonID,
                policyNo = it.policyNo,
                amount = it.amount
            )
        }
    }

    LaunchedEffect(policyList) {
        Log.d("PolicyPage", "Policy List: $policyList")
    }

    LaunchedEffect(policyAddonList) {
        Log.d("PolicyAddonPage", "Policy Addon List: $policyAddonList")
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
                    value = form.addonID,
                    onValueChange = { form = form.copy(addonID = it) },
                    label = { Text("Addon ID") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = expandedPolicyNo,
                    onExpandedChange = { expandedPolicyNo = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedPolicyNo,
                        onValueChange = { selectedPolicyNo = it },
                        label = { Text("Policy No", style = TextFieldStyle()) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
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
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = form.amount,
            onValueChange = { form = form.copy(amount = it) },
            label = { Text("Amount", style = TextFieldStyle()) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (form.addonID.isNotEmpty() && form.policyNo.isNotEmpty()) {
                            val newPolicyAddon = PolicyAddonDataClassItem(
                                addonID = form.addonID,
                                policyNo = form.policyNo,
                                amount = form.amount
                            )
                            policyAddonViewModel.addPolicyAddon(form.policyNo, form.addonID, newPolicyAddon, context)
                            form = PolicyAddonForm()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("ADD", style = ButtonTextFieldStyle())
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (form.addonID.isNotEmpty() && form.policyNo.isNotEmpty()) {
                            policyAddonViewModel.getPolicyAddonByID(form.policyNo, form.addonID, context)
                        }
                    },
                    modifier = Modifier.weight(1f)
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
                        if (form.addonID.isNotEmpty() && form.policyNo.isNotEmpty()) {
                            val updatePolicyAddOn = PolicyAddonDataClassItem(
                                addonID = form.addonID,
                                policyNo = form.policyNo,
                                amount = form.amount
                            )
                            policyAddonViewModel.updatePolicyAddon(form.policyNo, form.addonID, updatePolicyAddOn, context)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("UPDATE", style = ButtonTextFieldStyle())
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (form.addonID.isNotEmpty()) {
                            policyAddonViewModel.deletePolicyAddon(form.policyNo, form.addonID, context)
                            form = PolicyAddonForm()
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
