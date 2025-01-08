package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.AgentDataClassItem
import com.singlepointsol.carinsurance.dataclass.CustomerQueryDataClassItem
import com.singlepointsol.carinsurance.dataclass.QueryResponseDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.AgentRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.CustomerQueryRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.QueryResponseRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QueryResponseViewModel: ViewModel() {

    private val _queryResponseData = MutableStateFlow<QueryResponseDataClassItem?>(null)
    val queryResponseData: StateFlow<QueryResponseDataClassItem?> = _queryResponseData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _queryResponseList = MutableStateFlow<List<QueryResponseDataClassItem>>(emptyList())
    val queryResponseList: StateFlow<List<QueryResponseDataClassItem>> = _queryResponseList

    private val _customerQueryList = MutableStateFlow<List<CustomerQueryDataClassItem>>(emptyList())
    val customerQueryList: StateFlow<List<CustomerQueryDataClassItem>> = _customerQueryList


    private val _agentList = MutableStateFlow<List<AgentDataClassItem>>(emptyList())
    val agentList: StateFlow<List<AgentDataClassItem>> = _agentList


    fun getQueryResponse(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = QueryResponseRetrofitInstance.queryResponse.getQueryResponse()
                if (response.isSuccessful) {
                    _queryResponseList.value = response.body() ?: emptyList()
                    Toast.makeText(context, "Query Response fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("QueryResponseViewModel", "Query Response fetched successfully")
                } else {
                    _queryResponseList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch Query Response: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("QueryResponseViewModel", "Failed to fetch Query Response: ${response.message()}")
                }
            } catch (e: Exception) {
                _queryResponseList.value = emptyList()
                Toast.makeText(context, "Error fetching Query Response", Toast.LENGTH_LONG).show()
                Log.e("QueryResponseViewModel", "Error fetching Query Response", e)
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
                    Toast.makeText(context, "Customer Query  fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerQueryViewModel", "Customer Query fetched successfully")
                } else {
                    _customerQueryList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch Customer Query: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerQueryViewModel", "Failed to fetch Customer Query: ${response.message()}")
                }
            } catch (e: Exception) {
                _customerQueryList.value = emptyList()
                Toast.makeText(context, "Error fetching Customer Query", Toast.LENGTH_LONG).show()
                Log.e("CustomerQueryViewModel", "Error fetching Customer Query", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getAgent(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = AgentRetrofitInstance.agentAPI.getAgent()
                if (response.isSuccessful) {
                    _agentList.value = response.body() ?: emptyList()
                    Toast.makeText(context, "Agent  fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("CustomerQueryViewModel", "Agent fetched successfully")
                } else {
                    _agentList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch Agent: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("CustomerQueryViewModel", "Failed to fetch Agent: ${response.message()}")
                }
            } catch (e: Exception) {
                _agentList.value = emptyList()
                Toast.makeText(context, "Error fetching Agent", Toast.LENGTH_LONG).show()
                Log.e("CustomerQueryViewModel", "Error fetching Agent", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getQueryResponseById(queryId: String,srNo: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = QueryResponseRetrofitInstance.queryResponse.getQueryResponseById(queryId,srNo)
                if (response.isSuccessful) {
                    _queryResponseData.value = response.body()
                    Toast.makeText(context,"Query Response  fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("QueryResponseViewModel", "Query Response fetched successfully")
                } else {
                    Toast.makeText(context,"Failed to get Query Response : \${response.message()", Toast.LENGTH_LONG).show()
                    _queryResponseData.value = null
                    val errorBody = response.errorBody()?.string()
                    Log.e("QueryResponseViewModel", "Failed to get Query Response : ${response.message()} | Error Body: $errorBody")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Failed to get Query Response", Toast.LENGTH_LONG).show()
                Log.e("QueryResponseViewModel", "Failed to get Query Response", e)
            }
        }
    }

    fun addQueryResponse(queryResponse : QueryResponseDataClassItem,  context: Context) {
        viewModelScope.launch {
            try {

                val response = QueryResponseRetrofitInstance.queryResponse.addQueryResponse(queryResponse)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Query Response added successfully", Toast.LENGTH_LONG).show()
                    Log.d("QueryResponseViewModel", "Query Response added successfully")
                } else {
                    Toast.makeText(context,"Failed to add Query Response : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("QueryResponseViewModel", "Failed to add Query Response : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error adding Query Response", Toast.LENGTH_LONG).show()
                Log.e("QueryResponseViewModel", "Error adding Query Response", e)
            }
        }
    }

    fun updateQueryResponse(queryId: String, srNo: String, queryResponse : QueryResponseDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                val response = QueryResponseRetrofitInstance.queryResponse.updateQueryResponse(queryId,srNo,queryResponse)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Query Response updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("QueryResponseViewModel", "Query Response updated successfully")
                } else {
                    Toast.makeText(context,"Failed to update Query Response : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("QueryResponseViewModel", "Failed to update Query Response : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error updating Query Response", Toast.LENGTH_LONG).show()
                Log.e("QueryResponseViewModel", "Error updating Query Response", e)
            }
        }
    }

    fun deleteQueryResponse(queryId: String,srNo: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = QueryResponseRetrofitInstance.queryResponse.deleteQueryResponse(queryId,srNo)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Query Response deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("QueryResponseViewModel", "Query Response deleted successfully")
                } else {
                    Toast.makeText(context,"Failed to delete Query Response : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("QueryResponseViewModel", "Failed to delete Query Response : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error deleting Query Response", Toast.LENGTH_LONG).show()
                Log.e("QueryResponseViewModel", "Error deleting Query Response", e)
            }
        }
    }
}