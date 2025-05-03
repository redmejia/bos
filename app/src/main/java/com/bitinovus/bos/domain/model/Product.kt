package com.bitinovus.bos.domain.model

import com.bitinovus.bos.data.local.entities.Order

data class Product(
    val productID: String = "",
    val name: String = "",
    val price: Long = 0L,
    val productImage: String = "",
    val barcodeImage: String = "",
    val items: Int = 0, // item counter
)

// id is foreign key of transaction
fun List<Product>.toOrderListWithId(orderID: Long): List<Order> {
    return this.map { item ->
        Order(
            orderID = orderID,
            productID = item.productID,
            name = item.name,
            price = item.price,
            items = item.items,
            productImage = item.productImage
        )
    }
}

