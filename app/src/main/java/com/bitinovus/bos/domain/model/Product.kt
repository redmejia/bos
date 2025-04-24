package com.bitinovus.bos.domain.model

data class Product(
    val productID: String = "",
    val name: String = "",
    val price: Long = 0L,
    val productImage: String = "",
    val barcodeImage: String = "",
    val items: Int = 0, // item counter
)
