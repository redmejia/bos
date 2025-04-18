package com.bitinovus.bos.data.remote.repository

import com.bitinovus.bos.data.remote.api.BosApi
import com.bitinovus.bos.data.remote.models.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BosApiRepositoryImpl(
    private val bosApi: BosApi,
) {
    suspend fun getProduct(barcodeID: String, token: String): ProductModel? =
        withContext(Dispatchers.IO) {
            val resp = bosApi.getProductByBarcodeID(barcode = barcodeID, token = token)
            if (resp.isSuccessful) {
                resp.body()
            } else {
                null
            }
        }
}