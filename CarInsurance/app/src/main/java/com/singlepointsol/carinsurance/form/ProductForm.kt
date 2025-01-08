package com.singlepointsol.carinsurance.form

data class ProductForm (
    val productID: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val productUIN: String = "",
    val insuredInterests: String = "",
    val policyCoverage: String = ""
)