package com.singlepointsol.carinsurance.screens.welcomescreen.drawer.forms.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.singlepointsol.carinsurance.form.ProductAddonForm
import com.singlepointsol.carinsurance.viewmodel.ProductAddonViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.ProductAddonDataClassItem
import com.singlepointsol.carinsurance.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddOnPage(modifier: Modifier = Modifier, viewModel: ProductAddonViewModel) {

    val productAddonViewModel: ProductAddonViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()

    var form by remember { mutableStateOf(ProductAddonForm()) }
    val productAddOnData by productAddonViewModel.productAddOnData.collectAsState()
    val context = LocalContext.current
    val isLoading by productAddonViewModel.isLoading.collectAsState()
    val productAddon by productAddonViewModel.productAddonList.collectAsState()
    var expandedProductAddon by remember { mutableStateOf(false) }
    var selectedProductAddon by remember { mutableStateOf("") }
    val productList by productViewModel.productList.collectAsState()
    var expandedProductList by remember { mutableStateOf(false) }
    var selectedProductList by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        productAddonViewModel.getProductAddon(context)
        productViewModel.getProduct(context)
    }

    LaunchedEffect(productAddOnData) {
        productAddOnData?.let {
            form = form.copy(
                addonID = it.addonID,
                productID = it.productID,
                addonTitle = it.addonTitle,
                addonDescription = it.addonDescription
            )
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedProductList,
                    onExpandedChange = { expandedProductList = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedProductList,
                        onValueChange = { selectedProductList = it },
                        label = { Text("Product ID", style = TextFieldStyle()) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProductList)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedProductList,
                        onDismissRequest = { expandedProductList = false }
                    ) {
                        productList.forEach { product ->
                            DropdownMenuItem(
                                text = { Text(product.productID) },
                                onClick = {
                                    selectedProductList = product.productID
                                    form = form.copy(productID = product.productID)
                                    expandedProductList = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.addonTitle,
                    onValueChange = { form = form.copy(addonTitle = it) },
                    label = { Text("Addon Title", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.addonDescription,
                    onValueChange = { form = form.copy(addonDescription = it) },
                    label = { Text("Addon Description", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                        if (form.addonID.isNotEmpty()) {
                            val newProductAddOn = ProductAddonDataClassItem(
                                addonID = form.addonID,
                                productID = form.productID,
                                addonTitle = form.addonTitle,
                                addonDescription = form.addonDescription
                            )
                            productAddonViewModel.addProductAddOn(newProductAddOn, context)
                            form = ProductAddonForm()
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                ) {
                    Text("ADD", style = ButtonTextFieldStyle())
                }

                Button(
                    onClick = {
                        if (form.addonID.isNotEmpty()) {
                            productAddonViewModel.getProductAddOnByID(form.productID, form.addonID, context)
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
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
                        if (form.addonID.isNotEmpty()) {
                            val updatedProductAddOn = ProductAddonDataClassItem(
                                addonID = form.addonID,
                                productID = form.productID,
                                addonTitle = form.addonTitle,
                                addonDescription = form.addonDescription
                            )
                            productAddonViewModel.updateProductAddOn(form.productID, form.addonID, updatedProductAddOn, context)
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                ) {
                    Text("UPDATE", style = ButtonTextFieldStyle())
                }

                Button(
                    onClick = {
                        if (form.addonID.isNotEmpty()) {
                            productAddonViewModel.deleteProductAddOn(form.productID, form.addonID, context)
                            form = ProductAddonForm()
                        }
                    },
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                ) {
                    Text("DELETE", style = ButtonTextFieldStyle())
                }
            }
        }
    }
}
