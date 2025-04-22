package com.bitinovus.bos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bitinovus.bos.presentaion.theme.BosTheme
import com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel.ScannerViewmodel
import androidx.lifecycle.ViewModelProvider
import com.bitinovus.bos.data.remote.api.BosApi
import com.bitinovus.bos.data.remote.config.BosApiConfig
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.presentaion.screens.app.App
import com.bitinovus.bos.presentaion.viewmodels.BosViewModelFactory
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import com.bitinovus.bos.presentaion.viewmodels.paymentviewmodel.PaymentViewmodel
import com.bitinovus.bos.presentaion.viewmodels.walletviewmodel.WalletViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var scannerViewmodel: ScannerViewmodel
    private lateinit var cartViewmodel: CartViewmodel
    private lateinit var paymentViewmodel: PaymentViewmodel
    private lateinit var walletViewmodel: WalletViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val bosApi = BosApiConfig.createService(BosApi::class.java)
        val repository = BosApiRepositoryImpl(bosApi)

        val factory = BosViewModelFactory(repository)
        scannerViewmodel = ViewModelProvider(this, factory)[ScannerViewmodel::class.java]
        cartViewmodel = ViewModelProvider(this, factory)[CartViewmodel::class.java]
        paymentViewmodel = ViewModelProvider(this, factory)[PaymentViewmodel::class.java]
        walletViewmodel = ViewModelProvider(this, factory)[WalletViewmodel::class.java]
        
        setContent {
            BosTheme {
                App(
                    scannerViewmodel = scannerViewmodel,
                    cartViewmodel = cartViewmodel,
                    paymentViewmodel = paymentViewmodel,
                    walletViewmodel = walletViewmodel
                )
            }
        }
    }
}
