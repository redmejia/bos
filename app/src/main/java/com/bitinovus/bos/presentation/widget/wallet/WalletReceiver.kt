package com.bitinovus.bos.presentation.widget.wallet

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidgetManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition

class WalletReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        CoroutineScope(Dispatchers.IO).launch {
            val balance = intent.getDoubleExtra("balance", 0.0)

            val manager = GlanceAppWidgetManager(context)
            val glanceIDS = manager.getGlanceIds(WalletWidget::class.java)

            glanceIDS.forEach { glanceId ->
                val prefs = getAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId)
                val mutablePrefs = prefs.toMutablePreferences()
                mutablePrefs[WalletWidgetKeys.balance] = balance

                updateAppWidgetState(
                    context,
                    PreferencesGlanceStateDefinition,
                    glanceId
                ) { mutablePrefs }

                WalletWidget().update(context, glanceId)
            }
        }
    }
}