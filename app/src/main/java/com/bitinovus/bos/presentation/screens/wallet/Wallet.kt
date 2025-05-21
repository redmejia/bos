package com.bitinovus.bos.presentation.screens.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.bitinovus.bos.presentation.components.calendar.Calendar
import com.bitinovus.bos.presentation.components.card.TransactionCard
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentation.ui.theme.PrimaryGreen00
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentation.viewmodels.walletviewmodel.WalletViewmodel

@Composable
fun Wallet(
    walletViewmodel: WalletViewmodel,
) {

    val wallerCalendar by walletViewmodel.walletCalendarState.collectAsState()
    val transactions by walletViewmodel.walletTransactionState.collectAsState()
    val balanceState by walletViewmodel.balanceState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .background(color = PrimaryBlue60)
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = "time now",
                    tint = PrimaryWhite00
                )
                Text(
                    wallerCalendar.todayMonthAndYear,
                    color = PrimaryWhite00
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PrimaryBlue60)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.balance).uppercase(),
                fontSize = 17.sp,
                color = PrimaryWhite00,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = "$$balanceState",
                fontSize = 30.sp,
                color = PrimaryWhite00,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Calendar(weekDay = wallerCalendar.weekDay, days = wallerCalendar.days)
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Text(
                    stringResource(id = R.string.trx_history),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }
            items(items = transactions) { trx ->
                TransactionCard(
                    modifier = Modifier.clickable {},
                    details = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(45.dp),
                                painter = painterResource(
                                    id = if (trx.type == "CASH") R.drawable.transaction_cash_paid
                                    else R.drawable.credit_card
                                ),
                                contentDescription = null,
                                tint = if (trx.type == "CASH") PrimaryGreen00 else PrimaryBlue60
                            )
                            Column {
                                Text(
                                    "# ${trx.id}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    walletViewmodel.formatTime(trx.time, "hh:mm:ss a"),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    if (trx.type == "CASH")
                                        stringResource(id = R.string.trx_cash_type).uppercase()
                                    else stringResource(id = R.string.trx_card_type).uppercase(),
                                    fontSize = 15.sp, fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    },
                    trxAmount = { // total or grand total transaction
                        Column {
                            Text(
                                "$${trx.total / 100.00}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
            item {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WalletPreview() {
    // Wallet()
}