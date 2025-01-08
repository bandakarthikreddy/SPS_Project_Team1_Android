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
import com.singlepointsol.carinsurance.form.ProductForm
import com.singlepointsol.carinsurance.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singlepointsol.carinsurance.R
import com.singlepointsol.carinsurance.components.ButtonTextFieldStyle
import com.singlepointsol.carinsurance.components.TextFieldStyle
import com.singlepointsol.carinsurance.dataclass.ProductDataClassItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPage(modifier: Modifier, viewModel: ProductViewModel) {
    
    val productViewModel: ProductViewModel = viewModel()

    var form by remember { mutableStateOf(ProductForm()) }
    val productData by productViewModel.productData.collectAsState()
    val context = LocalContext.current
    val isLoading by productViewModel.isLoading.collectAsState()
    val productList by productViewModel.productList.collectAsState()
    var expandedProductID by remember { mutableStateOf(false) }
    var selectedProductID by remember { mutableStateOf("") }
    var expandedInsuredInterests by remember { mutableStateOf(false) }
    var selectedInsuredInterests by remember { mutableStateOf("") }

    LaunchedEffect(productData) {
        productData?.let {
            form = form.copy(
                productID = it.productID,
                productName = it.productName,
                productDescription = it.productDescription,
                productUIN = it.productUIN,
                insuredInterests = it.insuredInterests,  // This remains as is
                policyCoverage = it.policyCoverage
            )
            // Preserve manual selection of insured interests
            selectedInsuredInterests = it.insuredInterests
        }
    }

    LaunchedEffect(Unit) {
        productViewModel.getProduct(context)
    }

    LaunchedEffect(productList) {
        Log.d("Product Page", "Product List: $productList")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                if (isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        color = colorResource(id = R.color.black)
                    )
                }


                    OutlinedTextField(
                        value = form.productID,
                        onValueChange = { form = form.copy(productID = it) },
                        label = { Text("Product ID") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }


            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = form.productName,
                    onValueChange = { form = form.copy(productName = it) },
                    label = { Text("Product Name", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.productDescription,
                    onValueChange = { form = form.copy(productDescription = it) },
                    label = { Text("Product Description", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.productUIN,
                    onValueChange = { form = form.copy(productUIN = it) },
                    label = { Text("Product UIN", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedInsuredInterests,
                    onExpandedChange = { expandedInsuredInterests = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedInsuredInterests,
                        onValueChange = { selectedInsuredInterests = it },
                        label = { Text("Insured Interests", style = TextFieldStyle()) },
                        readOnly = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedInsuredInterests)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    if (productList.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = expandedInsuredInterests,
                            onDismissRequest = { expandedInsuredInterests = false }
                        ) {
                            listOf("Public Car", "Private Car").forEach { car ->
                                DropdownMenuItem(
                                    text = { Text(car) },
                                    onClick = {
                                        selectedInsuredInterests = car
                                        form = form.copy(insuredInterests = car)
                                        expandedInsuredInterests = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = form.policyCoverage,
                    onValueChange = { form = form.copy(policyCoverage = it) },
                    label = { Text("Policy Coverage", style = TextFieldStyle()) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Arranging the buttons in two columns and two rows
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (form.productID.isNotEmpty()) {
                        val newProduct = ProductDataClassItem(
                            productID = form.productID,
                            productName = form.productName,
                            productDescription = form.productDescription,
                            productUIN = form.productUIN,
                            insuredInterests = form.insuredInterests,
                            policyCoverage = form.policyCoverage
                        )
                        productViewModel.addProduct(form.productID, newProduct, context)
                        form = ProductForm()
                    }
                },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("ADD", style = ButtonTextFieldStyle())
                }

                Button(onClick = {
                    if (form.productID.isNotEmpty()) {
                        productViewModel.fetchProductById(form.productID, context)
                    }
                },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("FETCH", style = ButtonTextFieldStyle())
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (form.productID.isNotEmpty()) {
                        val updatedProduct = ProductDataClassItem(
                            productID = form.productID,
                            productName = form.productName,
                            productDescription = form.productDescription,
                            productUIN = form.productUIN,
                            insuredInterests = form.insuredInterests,
                            policyCoverage = form.policyCoverage
                        )
                        productViewModel.updateProduct(form.productID, updatedProduct, context)
                    }
                },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("UPDATE", style = ButtonTextFieldStyle())
                }

                Button(onClick = {
                    if (form.productID.isNotEmpty()) {
                        productViewModel.deleteProduct(form.productID, context)
                        form = ProductForm()
                    }
                },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("DELETE", style = ButtonTextFieldStyle())
                }
            }
        }
    }
}
