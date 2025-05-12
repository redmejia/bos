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

// Order list history
data class OrderHistory(
    val id: Long = 0,
    val name: String = "",
    val price: Long = 0L,
    val items: Int = 0,
    val productImage: String = "",
    val time: Long = 0L,
    val type: String = "",
    val total: Long = 0L,
    val trxAmount: Long = 0L,
    val trxExecuted: Boolean = false,
    val change: Long = 0L,
)

data class OrderList(
    val id: Long,
    var order: List<Order>,
    var transaction: Transaction,
)

fun List<OrderHistory>.toOrderList(): List<OrderList> {
    return this
        .groupBy { it.id } // Group all OrderHistory entries by transaction ID
        .map { (id, groupedItems) ->
            OrderList(
                id = id,
                order = groupedItems.map {
                    Order(
                        id = 0,
                        orderID = it.id,
                        productID = "",
                        name = it.name,
                        price = it.price,
                        productImage = it.productImage,
                        items = it.items
                    )
                },
                transaction = Transaction(
                    id = id,
                    trxExecuted = groupedItems.first().trxExecuted,
                    time = groupedItems.first().time,
                    type = groupedItems.first().type,
                    total = groupedItems.first().total,
                    trxAmount = groupedItems.first().trxAmount,
                    change = groupedItems.first().change,
                )
            )
        }
}
