package com.example.neversitupsampleapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.neversitupsampleapp.data.CurrentPriceDbData

@Database(entities = [CurrentPriceDbData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun priceDao(): PriceDao
}