package com.bitinovus.bos.data.remote.repository

import com.bitinovus.bos.data.remote.api.BosApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BosApiRepositoryImpl(
    private val bosApi: BosApi,
) {
    suspend fun getProduct(barcodeID: String) = withContext(Dispatchers.IO) {
        bosApi.getProductByBarcodeID(barcodeID)
    }
}