package com.example.neversitupsampleapp.repositories

import com.example.neversitupsampleapp.network.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getCurrentPrice() = apiHelper.getCurrentPrice()
}