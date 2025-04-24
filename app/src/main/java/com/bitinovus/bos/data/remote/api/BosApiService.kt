package com.bitinovus.bos.data.remote.api

import com.bitinovus.bos.data.remote.models.ProductDto
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query


interface BosApiService {
    @GET("product")
    suspend fun getProductByBarcodeID(
        @Query("barcode") barcode: String,
        @Header("Authorization") token: String,
    ): Response<ProductDto>
}