package com.bitinovus.bos.presentaion.viewmodels.cartviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.bos.data.remote.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewmodel : ViewModel() {

    private val _cartState = MutableStateFlow<List<Product>>(emptyList())
    val cartState: StateFlow<List<Product>> = _cartState.asStateFlow()

//    private val _cartSummaryState = MutableStateFlow()

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val currentCart = _cartState.value.toMutableList()
                val productExistIndex =
                    _cartState.value.indexOfFirst { it.productID == product.productID }

                if (productExistIndex != -1) {
                    val currentProduct = currentCart[productExistIndex]
                    currentCart[productExistIndex] = currentProduct.copy(
                        items = currentProduct.items + 1
                    )
                } else {
                    currentCart.add(product.copy(items = 1))
                }
                _cartState.value = currentCart

            } catch (e: Exception) {
                Log.e("ERROR", "addToCart: ${e.message}")
            }
        }
    }

    fun incrementItemCounter(productID: String) {
        viewModelScope.launch {
            try {
                val currentCart = _cartState.value.toMutableList()
                val productExistIndex =
                    _cartState.value.indexOfFirst { it.productID == productID }
                Log.d("COUNTER", "incrementItemCounter: COUNTER $productExistIndex")
                if (productExistIndex != -1) {
                    val currentProduct = currentCart[productExistIndex]
                    Log.d("COUNTER", "incrementItemCounter: COUNTER $currentProduct")
                    currentCart[productExistIndex] = currentProduct.copy(
                        items = currentProduct.items + 1
                    )
                }
                _cartState.value = currentCart

            } catch (e: Exception) {
                Log.e("ERROR", "addToCart: ${e.message}")
            }
        }
    }

    fun decrementItemCounter(productID: String) {
        viewModelScope.launch {
            try {
                val currentCart = _cartState.value.toMutableList()
                val productExistIndex =
                    _cartState.value.indexOfFirst { it.productID == productID }

                if (productExistIndex != -1) {
                    val currentProduct = currentCart[productExistIndex]
                    if (currentProduct.items > 1) {
                        currentCart[productExistIndex] = currentProduct.copy(
                            items = currentProduct.items - 1
                        )
                    }
                    // else {
                    // remove if 0
                    //     currentCart.removeAt(productExistIndex)
                    // }
                }
                _cartState.value = currentCart

            } catch (e: Exception) {
                Log.e("ERROR", "addToCart: ${e.message}")
            }
        }
    }

    fun removeItemFromCart(productID: String) {
        viewModelScope.launch {
            try {
                _cartState.value = _cartState.value.filter { it.productID != productID }
            } catch (e: Exception) {
                Log.e("ERROR", "addToCart: ${e.message}")
            }
        }
    }
}