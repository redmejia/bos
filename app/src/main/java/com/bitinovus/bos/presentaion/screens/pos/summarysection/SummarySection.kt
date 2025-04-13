package com.bitinovus.bos.presentaion.screens.pos.summarysection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.presentaion.components.summarysection.SummarySection
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGrayBase80

@Composable
fun SummaryContainer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryGrayBase80, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        SummarySection(
            modifier = Modifier.fillMaxWidth(),
            leadingText = "Total",
            trailingText = "$ 40.00",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        )
        SummarySection(
            modifier = Modifier.fillMaxWidth(),
            leadingText = "Total items",
            trailingText = "4",
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
        )
    }
}
