package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.CustomerDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.CustomerRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CustomerViewModel: ViewModel() {
    private val _customerData = MutableStateFlow<CustomerDataClassItem?>(null)
    val customerData: StateFlow<CustomerDataClassItem?> = _customerData

    private val _customerList = MutableStateFlow<List<CustomerDataClassItem>>(emptyList())
    val customerList: StateFlow<List<CustomerDataClassItem>> = _customerList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun getCustomer(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = CustomerRetrofitInstance.customerAPI.getCustomer()
                if (response.isSuccessful) {
                    _customerList.value = response.body() ?: emptyList()
                    Toast.makeText(context, "Customer data fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerViewModel", "Customer fetched successfully")
                } else {
                    _customerList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch customer: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerViewModel", "Failed to fetch customer: ${response.message()}")
                }
            } catch (e: Exception) {
                _customerList.value = emptyList()
                Toast.makeText(context, "Error fetching customer", Toast.LENGTH_LONG).show()
                Log.e("CustomerViewModel", "Error fetching customer", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fetch customer data by ID
    fun getCustomerById(customerId: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = CustomerRetrofitInstance.customerAPI.getCustomerById(customerId)
                if (response.isSuccessful) {
                    _customerData.value = response.body()
                    Toast.makeText(context, "Customer data fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerViewModel", "Customer data fetched successfully")
                } else {
                    _customerData.value = null
                    Toast.makeText(context, "Failed to fetch customer data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerViewModel", "Failed to fetch customer data: ${response.message()}")
                }
            } catch (e: Exception) {
                _customerData.value = null
                Toast.makeText(context, "Error fetching customer data", Toast.LENGTH_LONG).show()
                Log.e("CustomerViewModel", "Error fetching customer data", e)
            }
        }
    }

    // Add a new customer
    fun addNewCustomer(id: String, addNewCustomer: CustomerDataClassItem, context: Context) {
        if (id.isBlank() || addNewCustomer.customerID.isBlank()) {
            Toast.makeText(context, "Customer ID is required", Toast.LENGTH_LONG).show()
            Log.e("CustomerViewModel", "Customer ID is missing")
            return
        }

        viewModelScope.launch {
            try {
                Log.d("CustomerViewModel", "Attempting to add customer with ID: $id")
                Log.d("CustomerViewModel", "Customer data being sent: $addNewCustomer")

                val response = CustomerRetrofitInstance.customerAPI.postCustomer(id, addNewCustomer)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Customer added successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerViewModel", "Customer added successfully")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(
                        "CustomerViewModel",
                        "Failed to add customer: Code=${response.code()}, Message=${response.message()}, ErrorBody=$errorBody"
                    )
                    Toast.makeText(context, "Failed to add customer: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("CustomerViewModel", "Error adding customer", e)
                Toast.makeText(context, "Error adding customer", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun updateCustomerData(updatedCustomer: CustomerDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                val response = CustomerRetrofitInstance.customerAPI.updateCustomer(
                    id = updatedCustomer.customerID,
                    customer = updatedCustomer
                )
                if (response.isSuccessful) {
                    Toast.makeText(context, "Customer updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerViewModel", "Customer updated successfully")
                } else {
                    Toast.makeText(context, "Failed to update customer: ${response.message()} - ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerViewModel", "Failed to update customer: ${response.message()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error updating customer", Toast.LENGTH_LONG).show()
                Log.e("CustomerViewModel", "Error updating customer", e)
            }
        }
    }


    // Delete customer data by ID
    fun deleteCustomerData(customerId: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = CustomerRetrofitInstance.customerAPI.deleteCustomer(customerId)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Customer data deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerViewModel", "Customer deleted successfully")
                } else {
                    Toast.makeText(context, "Failed to delete customer: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerViewModel", "Failed to delete customer: ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error deleting customer", Toast.LENGTH_LONG).show()
                Log.e("CustomerViewModel", "Error deleting customer", e)
            }
        }
    }
}