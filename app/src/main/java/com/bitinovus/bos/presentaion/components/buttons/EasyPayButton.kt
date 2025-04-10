package com.bitinovus.bos.presentaion.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00

@Composable
fun EasyPayButton(
    onClick: () -> Unit = {},
    denomination: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
) {
    FilledTonalButton(
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue80,
            contentColor = PrimaryWhite00
        ),
        onClick = onClick
    ) {
        Text(text = denomination, style = textStyle)
    }
}


@Preview(showBackground = true)
@Composable
fun EasyPayButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EasyPayButton(denomination = "1.40")
    }
}