package com.bitinovus.bos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    // Use this instance for Widget update balance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

const val DB_VERSION = 1
const val DB_NAME = "bosdb"