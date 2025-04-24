package com.bitinovus.bos.data.remote.models

import com.google.gson.annotations.SerializedName
import com.bitinovus.bos.domain.model.Product as DomainProduct

data class ProductDto(
    @SerializedName("product") val product: Product,
    @SerializedName("status") val status: String,
)

data class Product(
    @SerializedName("product_id") val productID: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Long,
    @SerializedName("product_image") val productImage: String,
    @SerializedName("barcode_image") val barcodeImage: String,
    // @SerializedName("items") val items: Int = 0, // item counter
)

fun Product.toDomainProduct(): DomainProduct {
    return DomainProduct(
        productID = productID,
        name = name,
        price = price,
        productImage = productImage,
        barcodeImage = barcodeImage,
        items = 0,
    )
}