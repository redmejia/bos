package com.bitinovus.bos.presentaion.components.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.bitinovus.bos.data.remote.models.ProductModel
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack98

@Composable
fun CartCard(
    product: ProductModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(// not need for business image product
                        color = PrimaryBlack98,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .width(110.dp)
                    .height(110.dp),
                model = product.product.productImage,
                contentDescription = product.product.name
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Product: ${product.product.name}")
                Text(text = "Price: $${product.product.price}")
            }
        }
    }

}