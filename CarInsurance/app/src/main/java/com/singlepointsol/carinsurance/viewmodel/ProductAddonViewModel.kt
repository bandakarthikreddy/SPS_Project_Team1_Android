package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.ProductAddonDataClassItem
import com.singlepointsol.carinsurance.dataclass.ProductDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.ProductAddonRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.ProductRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductAddonViewModel: ViewModel() {

    private val _productAddOnData = MutableStateFlow<ProductAddonDataClassItem?>(null)
    val productAddOnData : StateFlow<ProductAddonDataClassItem?> = _productAddOnData


    private val _productAddonList = MutableStateFlow<List<ProductAddonDataClassItem>>(emptyList())
    val productAddonList: StateFlow<List<ProductAddonDataClassItem>> = _productAddonList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _productList = MutableStateFlow<List<ProductDataClassItem>>(emptyList())
    val productList: StateFlow<List<ProductDataClassItem>> = _productList



    fun getProductAddon(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ProductAddonRetrofitInstance.productAddOnAPI.getProductAddOn()
                if (response.isSuccessful) {
                    _productAddonList.value = response.body() ?: emptyList()
                    Log.d("ProductAddonViewModel", "Product Addon fetched successfully")
                } else {
                    _productAddonList.value = emptyList()
                    Log.e("ProductAddonViewModel", "Failed to fetch product add on: ${response.message()}")
                }
            } catch (e: Exception) {
                _productAddonList.value = emptyList()
                Log.e("ProductAddonViewModel", "Error fetching product add on", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProduct(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ProductRetrofitInstance.productAPI.getProduct()
                if (response.isSuccessful) {
                    _productList.value = (response.body() ?: emptyList())
                    Log.d("ProductViewModel", "Product fetched successfully")
                } else {
                    _productList.value = emptyList()
                    Log.e("ProductViewModel", "Failed to fetch product: ${response.message()}")
                }
            } catch (e: Exception) {
                _productList.value = emptyList()
                Log.e("ProductViewModel", "Error fetching product", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductAddOnByID(productID: String, addonID: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = ProductAddonRetrofitInstance.productAddOnAPI.getProductAddOnByID(productID, addonID)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        // Process the data and update the UI
                        _productAddOnData.value = data
                        Toast.makeText(context, "Product Addon Fetched Successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Product Addon not found", Toast.LENGTH_LONG).show()
                        Log.e("productAddonViewModel", "Empty response body")
                    }
                } else {
                    Toast.makeText(context, "Error fetching Product Addon", Toast.LENGTH_LONG).show()
                    Log.e("productAddonViewModel", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("productAddonViewModel", "Exception in fetch productAddon: ${e.message}")
            }
        }
    }


    fun addProductAddOn(productAddon: ProductAddonDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                val response = ProductAddonRetrofitInstance.productAddOnAPI.addProductAddOn(productAddon)

                if (response.isSuccessful) {
                    Log.e("productAddonViewModel", "")
                    Toast.makeText(context, "New product Addon Added Successfully", Toast.LENGTH_LONG).show()
                    Log.d("productAddonViewModel", "New product Addon Added Successfully")
                } else {
                    Log.e("productAddonViewModel", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Error: ${response.code()} - ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                    Log.e("productAddonViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("productAddonViewModel", "Exception in add productAddon: ${e.message}")
            }
        }
    }

    fun updateProductAddOn(addonID: String, productID: String, productAddon: ProductAddonDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("productAddonViewModel", "Updating with params: productID=$productID, addonId=$addonID")
                val response = ProductAddonRetrofitInstance.productAddOnAPI.updateProductAddOn(addonID, productID, productAddon)

                if (response.isSuccessful) {
                    Toast.makeText(context, "product Addon Updated Successfully", Toast.LENGTH_LONG).show()
                    Log.d("productAddonViewModel", "product Addon Updated Successfully")
                } else {
                    Toast.makeText(context, "product Addon Not Updated Successfully", Toast.LENGTH_LONG).show()
                    Log.e("productAddonViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("productAddonViewModel", "Exception in updateproductAddon: ${e.message}")
            }
        }
    }

    fun deleteProductAddOn(addonID: String, productID: String, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("productAddonViewModel", "Deleting with params: productID=$productID, addonId=$addonID")
                val response = ProductAddonRetrofitInstance.productAddOnAPI.deleteProductAddOn(addonID, productID)

                if (response.isSuccessful) {
                    Toast.makeText(context, "product Addon Deleted Successfully", Toast.LENGTH_LONG).show()
                    Log.d("productAddonViewModel", "product Addon Deleted Successfully")
                } else {
                    Toast.makeText(context, "product Addon Not Deleted Successfully", Toast.LENGTH_LONG).show()
                    Log.e("productAddonViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("productAddonViewModel", "Exception in delete productAddon: ${e.message}")
            }
        }
    }

}