package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.constants.Token
import com.singlepointsol.carinsurance.constants.Token.AUTH_TOKEN
import com.singlepointsol.carinsurance.dataclass.CustomerDataClassItem
import com.singlepointsol.carinsurance.dataclass.CustomerQueryDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.CustomerQueryRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.CustomerRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CustomerQueryViewModel: ViewModel() {

    val _customerQueryData = MutableStateFlow<CustomerQueryDataClassItem?>(null)
    val customerQueryData : StateFlow<CustomerQueryDataClassItem?> = _customerQueryData

    private val _customerQueryList = MutableStateFlow<List<CustomerQueryDataClassItem>>(emptyList())
    val customerQueryList: StateFlow<List<CustomerQueryDataClassItem>> = _customerQueryList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _customerList = MutableStateFlow<List<CustomerDataClassItem>>(emptyList())
    val customerList: StateFlow<List<CustomerDataClassItem>> = _customerList



    fun getCustomer(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = CustomerRetrofitInstance.customerAPI.getCustomer()
                if (response.isSuccessful) {
                    Toast.makeText(context,"Customer fetched successfully", Toast.LENGTH_LONG).show()
                    _customerList.value = response.body() ?: emptyList()
                    Log.d("CustomerViewModel", "Customer fetched successfully")
                } else {
                    _customerList.value = emptyList()
                    Toast.makeText(context,"Failed to fetch customer: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerViewModel", "Failed to fetch customer: ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Error fetching customer", Toast.LENGTH_LONG).show()
                _customerList.value = emptyList()
                Log.e("CustomerViewModel", "Error fetching customer", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getCustomerQuery(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = CustomerQueryRetrofitInstance.customerQueryAPI.getCustomerQuery()
                if (response.isSuccessful) {
                    _customerQueryList.value = response.body() ?: emptyList()
                    Toast.makeText(context,"Customer Query  fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerQueryViewModel", "Customer Query fetched successfully")
                } else {
                    _customerQueryList.value = emptyList()
                    Toast.makeText(context,"Failed to fetch customer query: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerQueryViewModel", "Failed to fetch customer query: ${response.message()}")
                }
            } catch (e: Exception) {
                _customerQueryList.value = emptyList()
                Toast.makeText(context,"Error fetching customer query", Toast.LENGTH_LONG).show()
                Log.e("CustomerQueryViewModel", "Error fetching customer query", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fetch Vehicle data by ID

    fun getCustomerQueryById(queryId: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = CustomerQueryRetrofitInstance.customerQueryAPI.getCustomerQueryById(queryId)
                if (response.isSuccessful) {
                    _customerQueryData.value = response.body()
                    Toast.makeText(context,"Customer Query  fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerQueryViewModal", "Customer Query fetched successfully")
                } else {
                    _customerQueryData.value = null
                    Toast.makeText(context,"Failed to get Customer Query : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerQueryViewModal", "Failed to get Customer Query : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Failed to get Customer Query", Toast.LENGTH_LONG).show()
                Log.e("CustomerQueryViewModal", "Failed to get Customer Query", e)
            }
        }
    }

    fun addCustomerQuery( queryId: String, addCustomerQuery : CustomerQueryDataClassItem, context: Context) {
        viewModelScope.launch {
            try {

                val response = CustomerQueryRetrofitInstance.customerQueryAPI.addCustomerQuery(queryId, addCustomerQuery)
                Log.d("CustomerQueryViewModal", "Response code: ${response.code()}")
                Log.d("CustomerQueryViewModal", "Response message: ${response.message()}")
                if (response.isSuccessful) {
                    Toast.makeText(context,"Customer query added successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerQueryViewModal", "Customer query added successfully")
                } else {
                    Toast.makeText(context,"Failed to add Customer query : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerQueryViewModal", "Failed to add Customer query : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error adding Customer query", Toast.LENGTH_LONG).show()
                Log.e("CustomerQueryViewModal", "Error adding Customer query", e)
            }
        }
    }

    fun updateCustomerQuery(customerQuery : CustomerQueryDataClassItem, queryId: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = CustomerQueryRetrofitInstance.customerQueryAPI.updateCustomerQuery(queryId,customerQuery)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Customer query updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerQueryViewModal", "Customer query updated successfully")
                } else {
                    Toast.makeText(context,"Failed to update Customer query : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerQueryViewModal", "Failed to update Customer query : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error updating Customer query", Toast.LENGTH_LONG).show()
                Log.e("CustomerQueryViewModal", "Error updating Customer query", e)
            }
        }
    }

    fun deleteCustomerQuery(queryId: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = CustomerQueryRetrofitInstance.customerQueryAPI.deleteCustomerQuery(queryId)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Customer query deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerQueryViewModal", "Customer query deleted successfully")
                } else {
                    Toast.makeText(context,"Failed to delete Customer query : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerQueryViewModal", "Failed to delete Customer query : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error deleting Customer query", Toast.LENGTH_LONG).show()
                Log.e("CustomerQueryViewModal", "Error deleting Customer query", e)
            }
        }
    }
}