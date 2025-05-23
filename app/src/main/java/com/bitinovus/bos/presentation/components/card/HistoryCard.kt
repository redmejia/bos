package com.bitinovus.bos.presentation.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bitinovus.bos.R
import com.bitinovus.bos.data.local.entities.OrderList
import com.bitinovus.bos.presentation.components.summarysection.SummarySection
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlack98
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.bos.presentation.viewmodels.paymentviewmodel.TransactionType
import com.bitinovus.bos.utils.currencyFormater

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    orderHistory: OrderList,
    date: () -> String, // transaction data order
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
        val sectionModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp)
        ) {
            SummarySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp, vertical = 6.dp),
                leadingText = "# ${orderHistory.id}", // order number or order ID
                trailingText = date(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            HorizontalDivider()
            orderHistory.order.forEach {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 8.dp),
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
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.quantity) + " ${it.items}",
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.End
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = currencyFormater("$", it.price / 100.00),
//                            text = String.format("$%.2f", it.price / 100.0),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp, vertical = 3.dp)
        ) {

            SummarySection(
                modifier = sectionModifier,
                leadingText = stringResource(id = R.string.total),
                trailingText = currencyFormater("$", orderHistory.transaction.total / 100.00),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            SummarySection(
                modifier = sectionModifier,
                leadingText = stringResource(id = R.string.payment_received),
                trailingText = if (orderHistory.transaction.trxAmount == 0L)
                    stringResource(id = R.string.exact).uppercase()
                else currencyFormater("$", orderHistory.transaction.trxAmount / 100.00),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            SummarySection(
                modifier = sectionModifier,
                leadingText = stringResource(id = R.string.change),
                trailingText = currencyFormater("$", orderHistory.transaction.change / 100.00),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            SummarySection(
                modifier = sectionModifier,
                leadingText = stringResource(id = R.string.type),
                trailingText = if (orderHistory.transaction.type == TransactionType.CASH.name)
                    stringResource(id = R.string.trx_cash_type)
                else "", // display the card type VISA, MasterCard ...
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
        }
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