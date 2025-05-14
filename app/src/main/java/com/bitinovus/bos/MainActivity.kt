package com.bitinovus.bos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.bitinovus.bos.presentation.theme.BosTheme
import com.bitinovus.bos.presentation.screens.app.App
import com.bitinovus.bos.presentation.viewmodels.walletviewmodel.WalletViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val walletViewmodel: WalletViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BosTheme {
                App()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        walletViewmodel.walletCalendar()
    }
}
