package com.bitinovus.bos.presentaion.screens.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00

@Composable
fun Wallet() {
    Column(
        modifier = Modifier.fillMaxSize(),
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
                        append("Your Balance")
                    }
                    append("\n")
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WalletPreview() {
    Wallet()
}