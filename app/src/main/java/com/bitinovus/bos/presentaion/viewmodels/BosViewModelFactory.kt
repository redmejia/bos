package com.bitinovus.bos.presentaion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.PaymentViewmodel
import com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel.ScannerViewmodel

class BosViewModelFactory(private val repositoryImpl: BosApiRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScannerViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScannerViewmodel(repositoryImpl) as T
        }
        if (modelClass.isAssignableFrom(CartViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewmodel() as T
        }
        if (modelClass.isAssignableFrom(PaymentViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentViewmodel() as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}