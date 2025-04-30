package com.bitinovus.bos.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey
    val id: Int, // order number
    @ColumnInfo(name = "time")
    val time: Long = 0L,
    @ColumnInfo(name = "trx_type")
    val type: String = "",
    @ColumnInfo(name = "trx_amount")
    val trxAmount: Long = 0L,
    @ColumnInfo(name = "trx_executed")
    val trxExecuted: Boolean = false,
    @ColumnInfo(name = "change")
    val change: Long = 0L,
)
