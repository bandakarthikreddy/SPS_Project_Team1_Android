package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.singlepointsol.carinsurance.dataclass.CustomerDataClassItem
import com.singlepointsol.carinsurance.dataclass.ProductDataClassItem
import com.singlepointsol.carinsurance.dataclass.ProposalDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.CustomerRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.ProductRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.ProposalRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProposalViewModel: ViewModel() {
    private val _proposalData = MutableStateFlow<ProposalDataClassItem?>(null)
    val proposalData: StateFlow<ProposalDataClassItem?> = _proposalData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _productList = MutableStateFlow<List<ProductDataClassItem>>(emptyList())
    val productList: StateFlow<List<ProductDataClassItem>> = _productList

    private val _customerList = MutableStateFlow<List<CustomerDataClassItem>>(emptyList())
    val customerList: StateFlow<List<CustomerDataClassItem>> = _customerList

    private val _proposalList = MutableStateFlow<List<ProposalDataClassItem>>(emptyList())
    val proposalList: StateFlow<List<ProposalDataClassItem>> = _proposalList


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


    fun getProposal(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ProposalRetrofitInstance.proposalAPI.getProposal()
                if (response.isSuccessful) {
                    _proposalList.value = response.body() ?: emptyList()
                    Toast.makeText(context, "Proposal data  fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("ProposalViewModel", "Proposal data fetched successfully")
                } else {
                    _proposalList.value = emptyList()
                    Toast.makeText(context, "Proposal to fetch product data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ProposalViewModel", "Proposal to fetch product data: ${response.message()}")
                }
            } catch (e: Exception) {
                _proposalList.value = emptyList()
                Toast.makeText(context, "Proposal fetching product", Toast.LENGTH_LONG).show()
                Log.e("ProposalViewModel", "Proposal fetching product", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProposalByID(proposalNo: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = ProposalRetrofitInstance.proposalAPI.getProposalByID(proposalNo)
                if (response.isSuccessful) {
                    _proposalData.value = response.body()
                    Toast.makeText(context, "Proposal details fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("ProposalViewModel", "Proposal details fetched successfully")
                } else {
                    _proposalData.value = null
                    Toast.makeText(context, "Failed to fetch Proposal data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ProposalViewModel", "Failed to fetch Proposal data: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ProposalViewModel", "Error fetching Proposal data", e)
                Toast.makeText(context, "Error fetching data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun addProposal(proposalNo: String, addProposal: ProposalDataClassItem, context: Context) {
        viewModelScope.launch {
            try {

                val gson = Gson()
                Log.d("ProposalViewModel", "Adding Proposal: ${gson.toJson(addProposal)}")
                val response = ProposalRetrofitInstance.proposalAPI.addProposal(proposalNo, addProposal)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Proposal details added successfully", Toast.LENGTH_LONG).show()
                    Log.d("ProposalViewModel", "Proposal details added successfully")
                } else {
                    Toast.makeText(context, "Failed to add proposal data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ProposalViewModel", "Failed to add proposal data: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ProposalViewModel", "Error adding proposal data", e)
                Toast.makeText(context, "Error adding data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateProposal(updateProposal: ProposalDataClassItem, proposalNo: String, context: Context) {
        viewModelScope.launch {
            try {
                val gson = Gson()
                Log.d("ProposalViewModel", "Updating Proposal:${gson.toJson(updateProposal)}")
                Log.d("ProposalViewModel", "Fetching Proposal with ID: $proposalNo")
                val response = ProposalRetrofitInstance.proposalAPI.updateProposal(proposalNo, updateProposal)
                if (response.isSuccessful) {
                    val gson = Gson()
                    Log.d("ProposalViewModel", "Fetched Proposal Data: ${gson.toJson(_proposalData.value)}")
                    Toast.makeText(context, "Proposal details updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("ProposalViewModel", "Proposal details updated successfully")
                } else {
                    Toast.makeText(context, "Failed to update proposal data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ProposalViewModel", "Failed to update proposal data: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ProposalViewModel", "Error updating proposal data", e)
                Toast.makeText(context, "Error updating data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteProposal(proposalNo: String, context: Context) {
        viewModelScope.launch {
            try {

                val response = ProposalRetrofitInstance.proposalAPI.deleteProposal(proposalNo)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Proposal details deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("ProposalViewModel", "Proposal details deleted successfully")
                } else {
                    Toast.makeText(context, "Failed to delete proposal data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ProposalViewModel", "Failed to delete proposal data: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ProposalViewModel", "Error deleting proposal data", e)
                Toast.makeText(context, "Error deleting data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}