package com.bitinovus.bos.data.remote.repository

import com.bitinovus.bos.data.remote.api.BosApiService
import com.bitinovus.bos.data.remote.models.toDomainProduct
import com.bitinovus.bos.domain.model.Product as DomainProduct
import com.bitinovus.bos.domain.repository.BosApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BosApiRepositoryImpl @Inject constructor(
    private val bosApi: BosApiService,
) : BosApiRepository {

    override suspend fun getProductByID(id: String, token: String): DomainProduct =
        withContext(Dispatchers.IO) {
            val product = try {
                val resp = bosApi.getProductByBarcodeID(barcode = id, token = token)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    body?.product?.toDomainProduct()
                }
                // return empty if not Successful
                DomainProduct()
            } catch (_: Exception) {
                // SerializationException error serialization
                DomainProduct()
            }
            product
        }
}