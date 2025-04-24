package com.bitinovus.bos.domain.repository

import com.bitinovus.bos.domain.model.Product

interface BosApiRepository {
    // Get scanned product
    suspend fun getProductByID(id: String, token: String): Product
}