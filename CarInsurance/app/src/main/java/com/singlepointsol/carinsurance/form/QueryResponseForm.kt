package com.singlepointsol.carinsurance.form

data class QueryResponseForm(
    val agentID: String = "",
    val description: String = "",
    val queryID: String = "",
    val responseDate: String = "",
    val srNo: String = ""
)
