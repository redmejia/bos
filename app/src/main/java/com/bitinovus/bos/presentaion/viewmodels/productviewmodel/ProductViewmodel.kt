package com.bitinovus.bos.presentaion.viewmodels.productviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.data.remote.models.ProductModel
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewmodel(private val repository: BosApiRepositoryImpl) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductModel?>(null)
    val uiState: StateFlow<ProductModel?> = _uiState.asStateFlow()

    fun getProduct(barcodeId: String) {
        viewModelScope.launch {
            val productModel = repository.getProduct(barcodeId)
            productModel?.let {
                _uiState.value = it
            }
        }
    }
}