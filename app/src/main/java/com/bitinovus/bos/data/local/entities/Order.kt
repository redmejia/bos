package com.bitinovus.bos.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo(name = "product_id") // unique id on db backend
    val productID: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "price")
    val price: Long = 0L,
    @ColumnInfo(name = "product_image")
    val productImage: String = "",
    @ColumnInfo(name = "qty")
    val items: Int = 0,
)
