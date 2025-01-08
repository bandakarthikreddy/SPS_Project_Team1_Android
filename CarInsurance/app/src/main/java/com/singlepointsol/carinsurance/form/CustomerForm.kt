package com.singlepointsol.carinsurance.form

data class CustomerForm(
    val customerID: String = "",
    val customerName: String = "",
    val customerPhone: String = "",
    val customerEmail: String = "",
    val customerAddress: String = ""
)
