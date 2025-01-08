package com.singlepointsol.carinsurance.form

data class PolicyForm(
    val policyNo: String = "",
    val proposalNo: String = "",
    val noClaimBonus: String = "",
    val receiptNo: String = "",
    val receiptDate: String = "",
    val paymentMode: String = "",
    val amount: String =""
)
