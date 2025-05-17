package com.bitinovus.bos.presentation.widget.wallet

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.unit.ColorProvider
import com.bitinovus.bos.R
import com.bitinovus.bos.domain.usecases.time.Time
import com.bitinovus.bos.presentation.ui.theme.PrimaryDarkBlue
import com.bitinovus.bos.presentation.ui.theme.PrimaryLTBlue
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentation.widget.wallet.WalletWidgetKeys.balance

object WalletWidgetKeys {
    val balance = doublePreferencesKey("balance")
}

class WalletWidget : GlanceAppWidget() {

    private val time = Time()

    override suspend fun provideGlance(context: Context, id: GlanceId) {
//        scheduleWorker(context)
        provideContent {
            val state = currentState<Preferences>()
            val balance = state[balance] ?: 0.0
            Wallet(context = context, balance = balance)
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun Wallet(context: Context, balance: Double) {
        Column(
            modifier = GlanceModifier
                .padding(8.dp)
                .fillMaxSize()
                .background(color = PrimaryWhite00),
        ) {
            Box(
                modifier = GlanceModifier
                    .padding(top = 10.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = GlanceModifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    val timeNow = time.now()
                    Row(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            provider = ImageProvider(R.drawable.calendar),
                            contentDescription = "calendar",
                            colorFilter = ColorFilter.tint(
                                colorProvider = ColorProvider(
                                    PrimaryDarkBlue
                                )
                            )
                        )
                        Spacer(modifier = GlanceModifier.width(2.dp))
                        Text(
                            text = time.formater(timeNow, "MMM dd, YYYY")
                                .replaceFirstChar { it.uppercase() },
                            style = TextStyle(
                                color = ColorProvider(color = PrimaryDarkBlue),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Box(
                    modifier = GlanceModifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.arrow_forward),
                        contentDescription = "go to wallet",
                        colorFilter = ColorFilter.tint(
                            colorProvider = ColorProvider(
                                PrimaryDarkBlue
                            )
                        )
                    )
                }
            }
            Column(
                modifier = GlanceModifier
                    .clickable(actionRunCallback<UpdateBalanceContent>())
                    .cornerRadius(10.dp)
                    .background(color = PrimaryLTBlue)
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.balance).uppercase(),
                    style = TextStyle(
                        color = ColorProvider(color = PrimaryDarkBlue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = GlanceModifier.height(5.dp))
                Text(
                    text = "$${String.format("%.2f", balance)}",
                    style = TextStyle(
                        color = ColorProvider(PrimaryDarkBlue),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}


class UpdateBalanceContent : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        val transactionWidgetRepositoryImpl = TransactionWidgetRepositoryImpl(context)
        val walletWidgetRepository = WalletWidgetTransaction(transactionWidgetRepositoryImpl)

        val balance = walletWidgetRepository.getBalance()

        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(WalletWidget::class.java)

        glanceIds.forEach { glanceId ->

            val prefs = getAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId)
            val mutablePrefs = prefs.toMutablePreferences()

            mutablePrefs[WalletWidgetKeys.balance] = balance

            updateAppWidgetState(
                context,
                PreferencesGlanceStateDefinition,
                glanceId
            ) { mutablePrefs }

            WalletWidget().updateAll(context)

        }

    }

}


