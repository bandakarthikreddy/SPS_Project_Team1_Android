package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.ClaimDataClassItem
import com.singlepointsol.carinsurance.dataclass.PolicyDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.ClaimRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.PolicyRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClaimViewModel: ViewModel() {

    private val _claimData = MutableStateFlow<ClaimDataClassItem?>(null)
    val claimData: StateFlow<ClaimDataClassItem?> = _claimData

    private val _policyList = MutableStateFlow<List<PolicyDataClassItem>>(emptyList())
    val policyList: StateFlow<List<PolicyDataClassItem>> = _policyList

    private val _claimList = MutableStateFlow<List<ClaimDataClassItem>>(emptyList())
    val claimList: StateFlow<List<ClaimDataClassItem>> = _claimList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun getClaim(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ClaimRetrofitInstance.claimAPI.getClaim()
                if (response.isSuccessful) {
                    _claimList.value = response.body() ?: emptyList()
                    Toast.makeText(context, "Claims fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("ClaimViewModel", "Claims fetched successfully")
                } else {
                    _claimList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch claims: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ClaimViewModel", "Failed to fetch claims: ${response.message()}")
                }
            } catch (e: Exception) {
                _claimList.value = emptyList()
                Toast.makeText(context, "Error fetching claims", Toast.LENGTH_LONG).show()
                Log.e("ClaimViewModel", "Error fetching claims", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getPolicy(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Set loading state to true before fetching data
                val response = PolicyRetrofitInstance.policyAPI.getPolicy()
                if (response.isSuccessful) {
                    _policyList.value = response.body() ?: emptyList()
                    Log.d("ClaimViewModel", "Policies fetched successfully")
                } else {
                    _policyList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch policies: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ClaimViewModel", "Failed to fetch policies: ${response.message()}")
                }
            } catch (e: Exception) {
                _policyList.value = emptyList()
                Toast.makeText(context, "Error fetching policies", Toast.LENGTH_LONG).show()
                Log.e("ClaimViewModel", "Error fetching policies", e)
            } finally {
                _isLoading.value = false // Ensure loading is stopped after the request completes
            }
        }
    }

    fun getClaimByClaimNo(claimNo: String, context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Set loading state to true before fetching data
                val response = ClaimRetrofitInstance.claimAPI.getClaimByClaimNo(claimNo)
                if (response.isSuccessful) {
                    _claimData.value = response.body()
                    Toast.makeText(context, "Claim details fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("ClaimViewModel", "Claim details fetched successfully")
                } else {
                    Toast.makeText(context, "Failed to fetch claim data: ${response.message()}", Toast.LENGTH_LONG).show()
                    _claimData.value = null
                    Log.e("ClaimViewModel", "Failed to fetch claim data: ${response.message()}")
                }
            } catch (e: Exception) {
                _claimData.value = null
                Toast.makeText(context, "Error fetching claim data", Toast.LENGTH_LONG).show()
                Log.e("ClaimViewModel", "Error fetching claim data", e)
            } finally {
                _isLoading.value = false // Ensure loading is stopped after the request completes
            }
        }
    }

    fun addClaim(claimNo: String, claim: ClaimDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Set loading state to true before adding data
                val response = ClaimRetrofitInstance.claimAPI.addClaim(claimNo, claim)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Claim details added successfully", Toast.LENGTH_LONG).show()
                    Log.d("ClaimViewModel", "Claim details added successfully")
                } else {
                    Toast.makeText(context, "Failed to add Claim data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ClaimViewModel", "Failed to add Claim data: ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error adding Claim data", Toast.LENGTH_LONG).show()
                Log.e("ClaimViewModel", "Error adding Claim data", e)
            } finally {
                _isLoading.value = false // Ensure loading is stopped after the request completes
            }
        }
    }

    fun updateClaim(updateClaim: ClaimDataClassItem, claimNo: String, context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Set loading state to true before updating data
                val response = ClaimRetrofitInstance.claimAPI.updateClaim(claimNo, updateClaim)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Claim details updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("ClaimViewModel", "Claim details updated successfully")
                } else {
                    Toast.makeText(context, "Failed to update Claim data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ClaimViewModel", "Failed to update Claim data: ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error updating Claim data", Toast.LENGTH_LONG).show()
                Log.e("ClaimViewModel", "Error updating Claim data", e)
            } finally {
                _isLoading.value = false // Ensure loading is stopped after the request completes
            }
        }
    }

    fun deleteClaim(claimNo: String, context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Set loading state to true before deleting data
                val response = ClaimRetrofitInstance.claimAPI.deleteClaim(claimNo)
                if (response.isSuccessful) {
                    Toast.makeText(context, "Claim details deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("ClaimViewModel", "Claim details deleted successfully")
                } else {
                    Toast.makeText(context, "Failed to delete Claim data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("ClaimViewModel", "Failed to delete Claim data: ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error deleting Claim data", Toast.LENGTH_LONG).show()
                Log.e("ClaimViewModel", "Error deleting Claim data", e)
            } finally {
                _isLoading.value = false // Ensure loading is stopped after the request completes
            }
        }
    }
}