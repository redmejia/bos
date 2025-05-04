package com.bitinovus.bos.presentaion.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bitinovus.bos.R
import com.bitinovus.bos.data.local.entities.OrderHistoryList
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack98
import com.bitinovus.bos.presentaion.ui.theme.PrimaryWhite00

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    orderHistory: OrderHistoryList,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = PrimaryWhite00
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Text(text = "${orderHistory.id}") // order number or order ID
        orderHistory.order.forEach {
            Row(
                modifier = Modifier
                    .padding(3.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .background( // not need for business image product
                            color = PrimaryBlack98,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    model = it.productImage,
                    contentDescription = it.name,
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Text(
                        text = it.name,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(id = R.string.quantity) + " ${it.items}",
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$${it.price / 100.0}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    }
                }
            }
        }
        Text(text = "${orderHistory.transaction.trxAmount}")
    }
}

@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    content: @Composable () -> Unit = {},
) {
    Card(
        modifier = modifier,
        colors = colors,
        elevation = elevation,
    ) {
        content()
    }
}