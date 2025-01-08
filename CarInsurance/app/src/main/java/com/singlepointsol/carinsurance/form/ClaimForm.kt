package com.singlepointsol.carinsurance.form

data class ClaimForm (
    val claimNo: String = "",
    val claimDate: String = "",
    val policyNo: String = "",
    val incidentDate: String = "",
    val incidentLocation: String = "",
    val incidentDescription: String = "",
    val claimAmount: String = "",
    val surveyorName: String = "",
    val surveyorPhone: String = "",
    val surveyorDate: String = "",
    val surveyorDescription: String = "",
    val claimStatus: String = ""
)