package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.PolicyAddonDataClassItem
import com.singlepointsol.carinsurance.dataclass.PolicyDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.PolicyAddonRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.PolicyRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PolicyAddonViewModel:ViewModel() {

    private val _policyAddonData = MutableStateFlow<PolicyAddonDataClassItem?>(null)
    val policyAddonData: StateFlow<PolicyAddonDataClassItem?> = _policyAddonData

    private val _policyAddonList = MutableStateFlow<List<PolicyAddonDataClassItem>>(emptyList())
    val policyAddonList: StateFlow<List<PolicyAddonDataClassItem>> = _policyAddonList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _policyList = MutableStateFlow<List<PolicyDataClassItem>>(emptyList())
    val policyList: StateFlow<List<PolicyDataClassItem>> = _policyList



    fun getPolicyAddon(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = PolicyAddonRetrofitInstance.policyAddonAPI.getPolicyAddon()
                if (response.isSuccessful) {
                    Toast.makeText(context, "PolicyAddon fetched successfully", Toast.LENGTH_LONG).show()
                    _policyAddonList.value = response.body() ?: emptyList()
                    Log.d("PolicyAddonViewModel", "PolicyAddon fetched successfully")
                } else {
                    _policyAddonList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch policy add on: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("PolicyAddonViewModel", "Failed to fetch policy add on: ${response.message()}")
                }
            } catch (e: Exception) {
                _policyAddonList.value = emptyList()
                Toast.makeText(context, "Error fetching policy add on", Toast.LENGTH_LONG).show()
                Log.e("PolicyAddonViewModel", "Error fetching policy add on", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getPolicy(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = PolicyRetrofitInstance.policyAPI.getPolicy()
                if (response.isSuccessful) {
                    Toast.makeText(context, "Policy fetched successfully", Toast.LENGTH_LONG).show()
                    _policyList.value = (response.body() ?: emptyList())
                    Log.d("PolicyViewModel", "Policy fetched successfully")
                } else {
                    Toast.makeText(context, "Failed to fetch policy: ${response.message()}", Toast.LENGTH_LONG).show()
                    _policyList.value = emptyList()
                    Log.e("PolicyViewModel", "Failed to fetch policy: ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error fetching policy", Toast.LENGTH_LONG).show()
                _policyList.value = emptyList()
                Log.e("PolicyViewModel", "Error fetching policy", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getPolicyAddonByID(policyNo: String, addonID: String, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("PolicyAddonViewModel", "Fetching with params: policyNo=$policyNo, addonId=$addonID")
                val response = PolicyAddonRetrofitInstance.policyAddonAPI.getPolicyAddonByID(policyNo, addonID)

                if (response.isSuccessful) {
                    Log.d("PolicyAddonViewModel", "Response Code: ${response.code()}")
                    Log.d("PolicyAddonViewModel", "Response Message: ${response.message()}")
                    Log.d("PolicyAddonViewModel", "Response Error Body: ${response.errorBody()?.string()}")
                    _policyAddonData.value = response.body()
                    Toast.makeText(context, "Policy Addon Fetched Successfully", Toast.LENGTH_LONG).show()
                    Log.d("PolicyAddonViewModel", "Policy Addon Fetched Successfully")
                } else {

                    Log.d("PolicyAddonViewModel", "Response Code: ${response.code()}")
                    Log.d("PolicyAddonViewModel", "Response Message: ${response.message()}")
                    Log.d("PolicyAddonViewModel", "Response Error Body: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Policy Addon Not Fetched Successfully", Toast.LENGTH_LONG).show()
                    Log.e("PolicyAddonViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {

                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("PolicyAddonViewModel", "Exception in fetchPolicyAddon: ${e.message}")
            }
        }
    }

    fun addPolicyAddon( policyNo: String, addonID: String,policyAddon: PolicyAddonDataClassItem, context: Context) {
        viewModelScope.launch {
            try {


                val response = PolicyAddonRetrofitInstance.policyAddonAPI.addPolicyAddon( policyAddon)

                if (response.isSuccessful) {
                    Log.d("PolicyAddonViewModel", "Request Body: $policyAddon")
                    Log.d("PolicyAddonViewModel", "Response Code: ${response.code()}")
                    Log.d("PolicyAddonViewModel", "Response Message: ${response.message()}")
                    Log.d("PolicyAddonViewModel", "Response Error Body: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "New Policy Addon Added Successfully", Toast.LENGTH_LONG).show()
                    Log.d("PolicyAddonViewModel", "New Policy Addon Added Successfully")
                } else {
                    Log.d("PolicyAddonViewModel", "Request Body: $policyAddon")
                    Log.d("PolicyAddonViewModel", "Response Code: ${response.code()}")
                    Log.d("PolicyAddonViewModel", "Response Message: ${response.message()}")
                    Log.d("PolicyAddonViewModel", "Response Error Body: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "New Policy Addon Not Added Successfully", Toast.LENGTH_LONG).show()
                    Log.e("PolicyAddonViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {

                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("PolicyAddonViewModel", "Exception in addPolicyAddon: ${e.message}")
            }
        }
    }

    fun updatePolicyAddon(policyNo: String,addonID: String, policyAddon: PolicyAddonDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("PolicyAddonViewModel", "Updating with params: policyNo=$policyNo, addonId=$addonID")
                val response = PolicyAddonRetrofitInstance.policyAddonAPI.updatePolicyAddon(policyNo,addonID, policyAddon)

                if (response.isSuccessful) {

                    Toast.makeText(context, "Policy Addon Updated Successfully", Toast.LENGTH_LONG).show()
                    Log.d("PolicyAddonViewModel", "Policy Addon Updated Successfully")
                } else {
                    Toast.makeText(context, "Policy Addon Not Updated Successfully", Toast.LENGTH_LONG).show()
                    Log.e("PolicyAddonViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("PolicyAddonViewModel", "Exception in updatePolicyAddon: ${e.message}")
            }
        }
    }

    fun deletePolicyAddon(policyNo: String,addonID: String, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("PolicyAddonViewModel", "Deleting with params: policyNo=$policyNo, addonId=$addonID")
                val response = PolicyAddonRetrofitInstance.policyAddonAPI.deletePolicyAddon(policyNo,addonID)

                if (response.isSuccessful) {
                    Toast.makeText(context, "Policy Addon Deleted Successfully", Toast.LENGTH_LONG).show()
                    Log.d("PolicyAddonViewModel", "Policy Addon Deleted Successfully")
                } else {
                    Toast.makeText(context, "Policy Addon Not Deleted Successfully", Toast.LENGTH_LONG).show()
                    Log.e("PolicyAddonViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("PolicyAddonViewModel", "Exception in deletePolicyAddon: ${e.message}")
            }
        }
    }
}