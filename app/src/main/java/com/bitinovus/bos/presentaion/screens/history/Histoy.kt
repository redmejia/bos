package com.bitinovus.bos.presentaion.screens.history

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bitinovus.bos.presentaion.components.card.CardContainer
import com.bitinovus.bos.presentaion.components.card.HistoryCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGrayBase80
import com.bitinovus.bos.presentaion.viewmodels.historyviewmodel.HistoryViewmodel
import com.bitinovus.bos.R
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentaion.ui.theme.PrimaryRed00
import com.bitinovus.bos.presentaion.ui.theme.PrimaryYellow00

@Composable
fun History(
    historyViewmodel: HistoryViewmodel,
) {
    val history by historyViewmodel.orderHistoryState.collectAsState()
    Log.d("ORDER", "ORDERS $history")

    var isDialogOpen by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            IconButton(onClick = { isDialogOpen = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_square),
                    contentDescription = "report"
                )
            }
        }
        item { Spacer(modifier = Modifier.height(4.dp)) }

        items(items = history, key = { it.id }) {
            CardContainer(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryGrayBase80
                ),
            ) {
                HistoryCard(
                    modifier = Modifier.padding(7.dp),
                    orderHistory = it,
                    date = {
                        historyViewmodel
                            .formatTime(it.transaction.time, "MMM d YYYY, hh:mm a")
                            .replaceFirstChar { it.uppercase() }
                    }
                )
            }
        }
        item { Spacer(modifier = Modifier.height(4.dp)) }
    }
    if (isDialogOpen) {
        Dialog(onDismissRequest = { isDialogOpen = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterHorizontally),
                        imageVector = Icons.Default.Warning,
                        contentDescription = "report",
                        tint = PrimaryYellow00
                    )
                    Text(
                        text = "This is a minimal dialog",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { historyViewmodel.writeReport() }
                        ) {
                            Text("Generate", color = PrimaryBlue60)
                        }
                        TextButton(
                            onClick = { isDialogOpen = false }
                        ) {
                            Text("Cancel", color = PrimaryRed00)
                        }
                    }
                }
            }
        }
    }
}


