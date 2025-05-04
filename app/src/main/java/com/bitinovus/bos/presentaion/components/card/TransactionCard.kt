package com.bitinovus.bos.presentaion.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.R

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    details: @Composable () -> Unit = {},
    trxAmount: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        details()
        trxAmount()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionCardPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        TransactionCard(
            details = {
                Row {
                    Icon(
                        modifier = Modifier.size(45.dp),
                        painter = painterResource(id = R.drawable.transaction_cash_paid),
                        contentDescription = null,
                    )
                    Column {
                        Text("1:20 PM")
                        Text("VISA")
                    }
                }
            },
            trxAmount = {
                Column {
                    Text("$90")
                }
            }
        )
    }
}


