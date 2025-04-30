package com.bitinovus.bos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitinovus.bos.data.local.dao.TransactionDao
import com.bitinovus.bos.data.local.entities.Transaction

@Database(
    entities = [
        Transaction::class
    ],
    version = DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}

const val DB_VERSION = 1
const val DB_NAME = "bosdb"