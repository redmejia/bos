package com.bitinovus.bos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bitinovus.bos.data.local.entities.Order
import com.bitinovus.bos.data.local.entities.OrderHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(order: List<Order>)

    @Query(
        """
SELECT 
tx.id,
o.name,
o.price,
o.qty AS items,
o.product_image AS productImage,
tx.time,
tx.trx_type AS type,
tx.trx_amount AS trxAmount,
tx.trx_executed AS trxExecuted,
tx.change
FROM transactions AS tx 
JOIN orders AS o ON tx.id = o.order_id
    """
    )
    fun history(): Flow<List<OrderHistory>>
}