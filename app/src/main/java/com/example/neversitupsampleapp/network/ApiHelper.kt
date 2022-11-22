package com.example.neversitupsampleapp.network

import com.example.neversitupsampleapp.data.CurrentPriceResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getCurrentPrice(): Response<CurrentPriceResponse>
}