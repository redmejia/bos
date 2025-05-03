package com.bitinovus.bos.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "order_id")
    val orderID: Long? = null,
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
