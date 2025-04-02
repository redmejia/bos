package com.bitinovus.bos.data.remote.api

import com.bitinovus.bos.data.remote.models.ProductModel
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query


interface BosApi {
    @GET("/product")
    suspend fun getProductByBarcodeID(
        @Query("barcode") barcode: String,
    ): Response<ProductModel>
}