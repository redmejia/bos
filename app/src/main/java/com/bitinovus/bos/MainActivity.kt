package com.bitinovus.bos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.bitinovus.bos.presentaion.theme.BosTheme
import com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel.ScannerViewmodel
import androidx.lifecycle.ViewModelProvider
import com.bitinovus.bos.data.remote.api.BosApi
import com.bitinovus.bos.data.remote.config.BosApiConfig
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.presentaion.screens.app.App
import com.bitinovus.bos.presentaion.viewmodels.BosViewModelFactory
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel

class MainActivity : ComponentActivity() {
    private lateinit var scannerViewmodel: ScannerViewmodel
    private lateinit var cartViewmodel: CartViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bosApi = BosApiConfig.createService(BosApi::class.java)
        val repository = BosApiRepositoryImpl(bosApi)

        val factory = BosViewModelFactory(repository)
        scannerViewmodel = ViewModelProvider(this, factory)[ScannerViewmodel::class.java]
        cartViewmodel = ViewModelProvider(this, factory)[CartViewmodel::class.java]

        setContent {
            BosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding),
                        scannerViewmodel = scannerViewmodel,
                        cartViewmodel = cartViewmodel
                    )
                }
            }
        }
    }
}
