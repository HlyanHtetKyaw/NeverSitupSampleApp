package com.example.neversitupsampleapp.network

import com.example.neversitupsampleapp.data.CurrentPriceResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("bpi/currentprice.json")
    suspend fun getCurrentPrice(): Response<CurrentPriceResponse>
}