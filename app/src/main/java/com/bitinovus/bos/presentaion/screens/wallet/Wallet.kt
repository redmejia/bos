package com.bitinovus.bos.presentaion.screens.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.R
import androidx.compose.runtime.getValue
import com.bitinovus.bos.presentaion.components.transactioncard.TransactionCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGreen00
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentaion.viewmodels.walletviewmodel.WalletViewmodel

@Composable
fun Wallet(
    walletViewmodel: WalletViewmodel,
) {

    val walletTransactionState by walletViewmodel.walletTransactionState.collectAsState()
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
                    walletViewmodel.todayDate(),
                    color = PrimaryWhite00
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.30f)
                .background(color = PrimaryBlue60),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 20.sp,
                            color = PrimaryWhite00,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(stringResource(id = R.string.balance).uppercase())
                    }
                    append("\n\n")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 30.sp,
                            color = PrimaryWhite00,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("$")
                        append("$balanceState")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

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
            items(items = walletTransactionState) { trx ->
                TransactionCard(
                    modifier = Modifier.clickable {},
                    details = {
                        Row {
                            Icon(
                                modifier = Modifier.size(45.dp),
                                painter = painterResource(
                                    id = if (trx.type.name == "CASH") R.drawable.transaction_cash_paid
                                    else R.drawable.credit_card
                                ),
                                contentDescription = null,
                                tint = if (trx.type.name == "CASH") PrimaryGreen00 else PrimaryBlue60
                            )
                            Column {
                                Text(
                                    walletViewmodel.formatTime(trx.time, "hh:mm:ss a"),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    if (trx.type.name == "CASH")
                                        stringResource(id = R.string.trx_cash_type).uppercase()
                                    else stringResource(id = R.string.trx_card_type).uppercase(),
                                    fontSize = 15.sp, fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    },
                    trxAmount = {
                        Column {
                            Text(
                                "$${trx.amount / 100.00}",
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