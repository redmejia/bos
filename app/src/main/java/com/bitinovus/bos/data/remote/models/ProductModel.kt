package com.bitinovus.bos.data.remote.models

import com.google.gson.annotations.SerializedName


data class ProductModel(
    val product: Product,
    val status: String,
)

data class Product(
    @SerializedName("product_id") val productID: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Long,
    @SerializedName("product_image") val productImage: String,
    @SerializedName("barcode_image") val barcodeImage: String,
    @SerializedName("items") val items: Int = 0, // item counter
)