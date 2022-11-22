package com.example.neversitupsampleapp.network

import com.example.neversitupsampleapp.data.CurrentPriceResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun getCurrentPrice(): Response<CurrentPriceResponse> =
        apiService.getCurrentPrice()
}