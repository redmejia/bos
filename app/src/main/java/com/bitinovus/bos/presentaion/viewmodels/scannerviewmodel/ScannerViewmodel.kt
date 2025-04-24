package com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.BuildConfig
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewmodel @Inject constructor(
    private val repository: BosApiRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Product>(Product())
    val uiState: StateFlow<Product> = _uiState.asStateFlow()

    fun getProduct(barcodeId: String) {
        val token = "Bearer ${BuildConfig.TOKEN}"
        viewModelScope.launch {
            _uiState.value = repository.getProductByID(id = barcodeId, token = token)
//            val productModel = repository.getProductByID(barcodeId, token = token)
//            if (productModel != null) {
//                _uiState.value = null // productModel
//            } else {
//                _uiState.value = null
//            }

        }
    }
}