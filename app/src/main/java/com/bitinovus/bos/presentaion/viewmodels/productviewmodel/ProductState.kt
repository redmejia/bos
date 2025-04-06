package com.bitinovus.bos.presentaion.viewmodels.productviewmodel

import com.bitinovus.bos.data.remote.models.ProductModel

data class ProductState(
    val product: ProductModel? = null,
    val productScannedAndFound: Boolean = false,
)
