package com.bitinovus.bos.data.remote.repository

import com.bitinovus.bos.data.remote.api.BosApi
import com.bitinovus.bos.data.remote.models.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BosApiRepositoryImpl(
    private val bosApi: BosApi,
) {
    suspend fun getProduct(barcodeID: String): ProductModel? =
        withContext(Dispatchers.IO) {
            val resp = bosApi.getProductByBarcodeID(barcodeID)
            if (resp.isSuccessful) {
                resp.body()
            } else {
                null
            }
        }
}