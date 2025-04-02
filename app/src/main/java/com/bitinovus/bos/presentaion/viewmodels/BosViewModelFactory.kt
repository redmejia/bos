package com.bitinovus.bos.presentaion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.presentaion.viewmodels.productviewmodel.ProductViewmodel

class BosViewModelFactory(private val repositoryImpl: BosApiRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewmodel(repositoryImpl) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }


}