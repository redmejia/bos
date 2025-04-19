package com.bitinovus.bos.presentaion.screens.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bitinovus.bos.presentaion.components.transactioncard.TransactionCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGreen00
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00

data class Transaction(
    val time: String,
    val type: String, // use trx enum type
    val amount: String,
)

@Composable
fun Wallet() {
    val trxList = listOf(
        Transaction(
            time = "1:20 PM",
            type = "VISA",
            amount = "300"
        ),
        Transaction(
            time = "2:00 PM",
            type = "VISA",
            amount = "3.00"
        ),
        Transaction(
            time = "3:20 PM",
            type = "CASH",
            amount = "340"
        ),
        Transaction(
            time = "11:20 PM",
            type = "VISA",
            amount = "10.0"
        ),
        Transaction(
            time = "12:20 PM",
            type = "CASH",
            amount = "3.0"
        ),
        Transaction(
            time = "4:20 PM",
            type = "CASH",
            amount = "34.0"
        ),
        Transaction(
            time = "5:20 PM",
            type = "CASH",
            amount = "53.00"
        ),
        Transaction(
            time = "7:20 PM",
            type = "VISA",
            amount = "0.8"
        ),
        Transaction(
            time = "9:20 PM",
            type = "CASH",
            amount = "0.8"
        ),
        Transaction(
            time = "8:20 PM",
            type = "VISA",
            amount = "0.8"
        ),
        Transaction(
            time = "8:20 PM",
            type = "Pay",
            amount = "0.70"
        ),
        Transaction(
            time = "8:20 PM",
            type = "MasterCard",
            amount = "0.50"
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
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
                        append("150")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
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
            items(items = trxList) { trx ->
                TransactionCard(
                    modifier = Modifier.clickable {},
                    details = {
                        Row {
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
                                    trx.time,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    if (trx.type == "CASH")
                                        stringResource(id = R.string.trx_cash_type).uppercase()
                                    else "${trx.type} ${stringResource(id = R.string.trx_card_type)}".uppercase(),
                                    fontSize = 15.sp, fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    },
                    trxAmount = {
                        Column {
                            Text(
                                "$${trx.amount}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WalletPreview() {
    Wallet()
}