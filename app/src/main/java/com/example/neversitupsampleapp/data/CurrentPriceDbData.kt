package com.example.neversitupsampleapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_table")
data class CurrentPriceDbData(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "usd_price") val usdPrice: String?,
    @ColumnInfo(name = "gbp_price") val gbpPrice: String?,
    @ColumnInfo(name = "eur_price") val eurPrice: String?
)