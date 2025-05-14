package com.bitinovus.bos.presentation.components.summarysection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun SummarySection(
    modifier: Modifier= Modifier,
    leadingText: String,
    trailingText: String,
    style: TextStyle = LocalTextStyle.current,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(leadingText, style = style)
        Text(trailingText, style = style)
    }
}