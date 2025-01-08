package com.singlepointsol.carinsurance.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.carinsurance.dataclass.AgentDataClassItem
import com.singlepointsol.carinsurance.retrofitinstance.AgentRetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AgentViewModel: ViewModel() {
    private val _agentData = MutableStateFlow<AgentDataClassItem?>(null)
    val agentData : StateFlow<AgentDataClassItem?> = _agentData

    private val _agentList = MutableStateFlow<List<AgentDataClassItem>>(emptyList())
    val agentList: StateFlow<List<AgentDataClassItem>> = _agentList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun getAgent(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = AgentRetrofitInstance.agentAPI.getAgent()
                if (response.isSuccessful) {
                    _agentList.value = response.body() ?: emptyList()
                    Toast.makeText(context,"Agent fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("AgentViewModel", "Agent fetched successfully")
                } else {
                    _agentList.value = emptyList()
                    Toast.makeText(context,"Failed to fetch agent: ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("AgentViewModel", "Failed to fetch agent: ${response.message()}")
                }
            } catch (e: Exception) {
                _agentList.value = emptyList()
                Toast.makeText(context,"Error fetching agent", Toast.LENGTH_LONG).show()
                Log.e("AgentViewModel", "Error fetching agent", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fetch Vehicle data by ID

    fun getAgentByAgentID(agentID: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = AgentRetrofitInstance.agentAPI.getAgentByAgentID(agentID)
                if (response.isSuccessful) {
                    _agentData.value = response.body()
                    Toast.makeText(context,"Agent fetched successfully", Toast.LENGTH_LONG).show()
                    Log.d("AgentViewModel", "Agent details fetched successfully")
                } else {
                    _agentData.value = null
                    Toast.makeText(context,"Failed to add Agent data : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("AgentViewModel", "Failed to add Agent data : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error fetching agent data", Toast.LENGTH_LONG).show()
                Log.e("AgentViewModel", "Error fetching agent data", e)
            }
        }
    }

    fun addAgent(agentID: String, addNewAgent: AgentDataClassItem, context: Context) {
        viewModelScope.launch {
            try {
                val response = AgentRetrofitInstance.agentAPI.addAgent(agentID,addNewAgent)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Agent added successfully", Toast.LENGTH_LONG).show()
                    Log.d("AgentViewModel", "Agent added successfully")
                } else {
                    Toast.makeText(context,"Failed to add Agent : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("AgentViewModel", "Failed to add Agent : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error adding Agent", Toast.LENGTH_LONG).show()
                Log.e("AgentViewModel", "Error adding Agent", e)
            }
        }
    }

    fun updateAgent(updateAgent : AgentDataClassItem, agentID: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = AgentRetrofitInstance.agentAPI.updateAgent(agentID,updateAgent)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Agent updated successfully", Toast.LENGTH_LONG).show()
                    Log.d("AgentViewModel", "Agent updated successfully")
                } else {
                    Toast.makeText(context,"Failed to update Agent : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("AgentViewModel", "Failed to update Agent : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error updating Agent", Toast.LENGTH_LONG).show()
                Log.e("AgentViewModel", "Error updating Agent", e)
            }
        }
    }

    fun deleteAgent(agentID: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = AgentRetrofitInstance.agentAPI.deleteAgent(agentID)
                if (response.isSuccessful) {
                    Toast.makeText(context,"Agent details deleted successfully", Toast.LENGTH_LONG).show()
                    Log.d("AgentViewModel", "Agent details deleted successfully")
                } else {
                    Toast.makeText(context,"Failed to delete Agent data : ${response.message()}", Toast.LENGTH_LONG).show()
                    Log.e("AgentViewModel", "Failed to delete Agent data : ${response.message()}")
                }
            } catch (e : Exception) {
                Toast.makeText(context,"Error deleting Agent data", Toast.LENGTH_LONG).show()
                Log.e("AgentViewModel", "Error deleting Agent data", e)
            }
        }
    }
}