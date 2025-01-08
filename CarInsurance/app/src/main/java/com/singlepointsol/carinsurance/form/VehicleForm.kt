package com.singlepointsol.carinsurance.form

data class VehicleForm (
    val regNo: String = "",
    val regAuthority: String = "",
    val ownerId: String = "",
    val make: String = "",
    val model: String = "",
    val fuelType: String = "",
    val variant: String = "",
    val engineNo: String = "",
    val chassisNo: String = "",
    val engineCapacity: String = "",
    val seatingCapacity: String = "",
    val mfgYear: String = "",
    val regDate: String = "",
    val bodyType: String = "",
    val leasedBy: String = ""
)