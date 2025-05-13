package com.bitinovus.bos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bitinovus.bos.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions")
    fun getAll(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT 1")
    fun getLast(): Flow<Transaction?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction): Long

    @Query("DELETE FROM transactions")
    suspend fun delete()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'transactions'")
    suspend fun reset()
}