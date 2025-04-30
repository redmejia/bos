package com.bitinovus.bos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bitinovus.bos.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    fun get(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions ORDER BY trx_type DESC LIMIT 1")
    fun getLast(): Flow<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(trx: Transaction)
}