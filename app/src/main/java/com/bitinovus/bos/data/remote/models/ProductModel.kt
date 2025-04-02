package com.bitinovus.bos.data.remote.models

data class ProductModel(
    val product: Product,
    val status: String,
)

data class Product(
    val productID: String,
    val name: String,
    val price: Long,
    val barcodeImage: String,
)