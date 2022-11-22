package com.example.neversitupsampleapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.neversitupsampleapp.data.CurrentPriceDbData

@Dao
interface PriceDao {
    @Query("SELECT * FROM price_table")
    fun getAll(): List<CurrentPriceDbData>

    @Insert
    fun insert(price: CurrentPriceDbData)

}