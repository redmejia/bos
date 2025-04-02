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
import com.bitinovus.bos.presentaion.viewmodels.productviewmodel.ProductViewmodel
import androidx.lifecycle.ViewModelProvider
import com.bitinovus.bos.data.remote.api.BosApi
import com.bitinovus.bos.data.remote.config.BosApiConfig
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.presentaion.screens.app.App
import com.bitinovus.bos.presentaion.viewmodels.BosViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var productViewmodel: ProductViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bosApi = BosApiConfig.createService(BosApi::class.java)
        val repository = BosApiRepositoryImpl(bosApi)

        val factory = BosViewModelFactory(repository)
        productViewmodel = ViewModelProvider(this, factory)[ProductViewmodel::class.java]

        setContent {
            BosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding),
                        productViewmodel = productViewmodel
                    )
                }
            }
        }
    }
}
