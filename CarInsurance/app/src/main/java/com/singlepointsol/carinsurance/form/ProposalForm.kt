package com.singlepointsol.carinsurance.form

data class ProposalForm(
    val agentID: String = "",
    val basicAmount: String = "",
    val customerID: String = "",
    val fromDate: String = "",
    val idv: String = "",
    val productID: String = "",
    val proposalNo: String = "",
    val regNo: String = "",
    val toDate: String = "",
    val totalAmount: String = ""
)