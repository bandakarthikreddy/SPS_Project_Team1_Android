package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.ProductDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.ProductRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val _productData = MutableStateFlow<ProductDataClassItem?>(null)
    val productData: StateFlow<ProductDataClassItem?> = _productData

    private val _productList = MutableStateFlow<List<ProductDataClassItem>>(emptyList())
    val productList: StateFlow<List<ProductDataClassItem>> = _productList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getProduct(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ProductRetrofitInstance.productAPI.getProduct()
                if (response.isSuccessful) {
                    _productList.value = response.body() ?: emptyList()
                    Toast.makeText(context, "Product data  fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerViewModel", "Product data fetched successfully")
                } else {
                    _productList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch product data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerViewModel", "Failed to fetch product data: ${response.message()}")
                }
            } catch (e: Exception) {
                _productList.value = emptyList()
                Toast.makeText(context, "Error fetching product", Toast.LENGTH_LONG).show()
                Log.e("CustomerViewModel", "Error fetching product", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchProductById(productID: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = ProductRetrofitInstance.productAPI.getProductById(productID)
                if (response.isSuccessful) {
                    _productData.value = response.body()
                    Toast.makeText(context, "Product fetched successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to fetch product: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun addProduct(productID: String,product: ProductDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                val response = ProductRetrofitInstance.productAPI.addProduct(productID, product)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Product added successfully", Toast.LENGTH_LONG).show()
                    Log.d("ProductViewModel", "Product added successfuly")
                } else {
                    Toast.makeText(context, "Failed to add product: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ProductViewModel", "Failed to add product: \${response.message()")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                Log.d("ProductViewModel", "${e.message}")
            }
        }
    }

    fun updateProduct(productID: String, product: ProductDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                val response = ProductRetrofitInstance.productAPI.updateProduct(productID, product)
                if (response.isSuccessful) {
                    Log.d("ProductViewModel", "Product updated successfully with no response body")
                    Toast.makeText(context, "Product updated successfully", Toast.LENGTH_LONG).show()
                } else {
                    Log.e("ProductViewModel", "Failed to update product")
                    Toast.makeText(context, "Failed to update product", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Exception during update: ${e.message}", e)
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteProduct(productID: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = ProductRetrofitInstance.productAPI.deleteProduct(productID)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("ProductViewModel", "Product deleted successfully")
                } else {
                    Toast.makeText(context, "Failed to delete product: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.d("ProductViewModel", "Failed to delete product: ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("ProductViewModel", "Error: ${e.message}", e)
            }
        }
    }
}