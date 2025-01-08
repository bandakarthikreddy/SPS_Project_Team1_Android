package com.singlepointsol.carinsurance.dataclass

data class QueryResponseDataClassItem(
    val agentID: String,
    val description: String,
    val queryID: String,
    val responseDate: String,
    val srNo: String
)
