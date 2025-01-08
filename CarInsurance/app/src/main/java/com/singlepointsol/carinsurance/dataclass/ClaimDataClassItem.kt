package com.singlepointsol.carinsurance.dataclass

data class ClaimDataClassItem(
    val claimAmount: String,
    val claimDate: String,
    val claimNo: String,
    val claimStatus: String,
    val incidentDate: String,
    val incidentDescription: String,
    val incidentLocation: String,
    val policyNo: String,
    val surveyDate: String,
    val surveyDescription: String,
    val surveyorName: String,
    val surveyorPhone: String
)
