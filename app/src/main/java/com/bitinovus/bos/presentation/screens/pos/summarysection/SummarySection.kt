package com.bitinovus.bos.presentation.screens.pos.summarysection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitinovus.bos.presentation.components.summarysection.SummarySection
import com.bitinovus.bos.presentation.ui.theme.PrimaryGrayBase80
import com.bitinovus.bos.presentation.viewmodels.cartviewmodel.CartSummaryState
import com.bitinovus.bos.R

// Summary purchase section
@Composable
fun SummaryContainer(isProductListEmpty: Boolean, cartSummaryState: CartSummaryState) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryGrayBase80, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        SummarySection(
            modifier = Modifier.fillMaxWidth(),
            leadingText = stringResource(id =  R.string.total),
            trailingText = "$${
                if (!isProductListEmpty) cartSummaryState.grandTotal / 100.00 else 0.0
            }",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        )
        SummarySection(
            modifier = Modifier.fillMaxWidth(),
            leadingText = stringResource(id =  R.string.total_items),
            trailingText = "${if (!isProductListEmpty) cartSummaryState.itemsInCart else 0}",
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
        )
    }
}
