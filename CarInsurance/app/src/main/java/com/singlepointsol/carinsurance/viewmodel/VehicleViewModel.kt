package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.CustomerDataClassItem
import com.singlepointsol.carinsurance.dataclass.VehicleDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.CustomerRetrofitInstance
import com.singlepointsol.carinsurance.retrofitinstance.VehicleRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VehicleViewModel: ViewModel() {
    private val _vehicleData = MutableStateFlow<VehicleDataClassItem?>(null)
    val vehicleData : StateFlow<VehicleDataClassItem?> = _vehicleData
    private val _owners = MutableStateFlow<VehicleDataClassItem?>(null)
    val owners : StateFlow<VehicleDataClassItem?> = _owners

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _vehicleList = MutableStateFlow<List<VehicleDataClassItem>>(emptyList())
    val vehicleList: StateFlow<List<VehicleDataClassItem>> = _vehicleList

    private val _customerList = MutableStateFlow<List<CustomerDataClassItem>>(emptyList())
    val customerList: StateFlow<List<CustomerDataClassItem>> = _customerList



    fun getVehicle(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = VehicleRetrofitInstance.vehicleAPI.getVehicle()
                if (response.isSuccessful) {
                    _vehicleList.value = response.body() ?: emptyList()
                    Toast.makeText(context, "Vehicle data fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("VehicleViewModel", "Vehicle fetched successfully")
                } else {
                    _vehicleList.value = emptyList()
                    Toast.makeText(context, "Failed to fetch Vehicle: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("VehicleViewModel", "Failed to fetch Vehicle: ${response.message()}")
                }
            } catch (e: Exception) {
                _vehicleList.value = emptyList()
                Toast.makeText(context, "Error fetching Vehicle", Toast.LENGTH_LONG).show()
                Log.e("VehicleViewModel", "Error fetching Vehicle", e)
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


    fun getOwners(ownerID: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = VehicleRetrofitInstance.vehicleAPI.getOwners(ownerID)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Owner data fetched successfully", Toast.LENGTH_LONG).show()
                    _owners.value = response.body() // Save the fetched owner data
                    Log.d("VehicleViewModel", "Owner data fetched successfully")
                } else {
                    Toast.makeText(context,"Failed to fetch owner data : ${response.message()}", Toast.LENGTH_LONG).show()
                    _owners.value = null
                    Log.e("VehicleViewModel", "Failed to fetch owner data : ${response.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(context,"Error fetching owner data", Toast.LENGTH_LONG).show()
                Log.e("VehicleViewModel", "Error fetching owner data", e)
            }
        }
    }
    // Fetch Vehicle data by ID

    fun getVehicleByRegNo(regNo: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = VehicleRetrofitInstance.vehicleAPI.getVehicleByRegNo(regNo)
                if (response.isSuccessful) {
                    _vehicleData.value = response.body()
                    Toast.makeText(context,"Vehicle details fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("VehicleViewModel", "Vehicle details fetched successfully")
                } else {
                    Toast.makeText(context,"Failed to vehicle data : ${response.message()}", Toast.LENGTH_LONG).show()
                    _vehicleData.value = null
                    Log.e("VehicleViewModel", "Failed to vehicle data : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error fetching vehicle data", Toast.LENGTH_LONG).show()
                Log.e("VehicleViewModel", "Error fetching vehicle data", e)
            }
        }
    }

    fun addVehicle(addVehicle: VehicleDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                val response = VehicleRetrofitInstance.vehicleAPI.addVehicle( addVehicle)
                // Log the data being sent
                Log.d("VehicleViewModel", "Sending vehicle data: $addVehicle")
                if (response.isSuccessful) {
                    Toast.makeText(context, "Failed to add vehicle data: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.d("VehicleViewModel", "Vehicle details added successfully")
                } else {
                    Toast.makeText(context, "Vehicle details added successfully", Toast.LENGTH_LONG).show()
                    Log.e("VehicleViewModel", "Vehicle details added successfully")
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error adding vehicle data", Toast.LENGTH_LONG).show()
                Log.e("VehicleViewModel", "Error adding vehicle data", e)
            }
        }
    }


    fun updateVehicle(updateVehicle : VehicleDataClassItem, regNo: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = VehicleRetrofitInstance.vehicleAPI.updateVehicle(regNo,updateVehicle)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Vehicle details updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("VehicleViewModel", "Vehicle details updated successfully")
                } else {
                    Toast.makeText(context,"Failed to update vehicle data : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("VehicleViewModel", "Failed to update vehicle data : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error updating vehicle data", Toast.LENGTH_LONG).show()
                Log.e("VehicleViewModel", "Error updating vehicle data", e)
            }
        }
    }

    fun deleteVehicle(regNo: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = VehicleRetrofitInstance.vehicleAPI.deleteVehicle(regNo)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Vehicle details deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("VehicleViewModel", "Vehicle details deleted successfully")
                } else {
                    Toast.makeText(context,"Failed to delete vehicle data : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("VehicleViewModel", "Failed to delete vehicle data : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error deleting vehicle data", Toast.LENGTH_LONG).show()
                Log.e("VehicleViewModel", "Error deleting vehicle data", e)
            }
        }
    }
}