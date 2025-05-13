package com.bitinovus.bos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitinovus.bos.data.local.dao.OrderDao
import com.bitinovus.bos.data.local.dao.TransactionDao
import com.bitinovus.bos.data.local.entities.Order
import com.bitinovus.bos.data.local.entities.Transaction

@Database(
    entities = [
        Transaction::class,
        Order::class
    ],
    version = DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun orderDao(): OrderDao
}

const val DB_VERSION = 1
const val DB_NAME = "bosdb"