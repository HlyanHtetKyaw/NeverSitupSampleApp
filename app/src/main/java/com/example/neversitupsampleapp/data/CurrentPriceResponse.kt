package com.example.neversitupsampleapp.data

data class CurrentPriceResponse(
    val bpi: Bpi? = null,
    val chartName: String? = null,
    val disclaimer: String? = null,
    val time: Time? = null
)