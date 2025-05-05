package com.bitinovus.bos.presentaion.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.components.card.CardContainer
import com.bitinovus.bos.presentaion.components.card.HistoryCard
import com.bitinovus.bos.presentaion.ui.theme.PrimaryGrayBase80
import com.bitinovus.bos.presentaion.viewmodels.historyviewmodel.HistoryViewmodel

@Composable
fun History(
    historyViewmodel: HistoryViewmodel,
) {
    val history by historyViewmodel.orderHistoryState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
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
                            .formatTime(it.transaction.time, "MMM YYYY")
                    },
                    time = {
                        historyViewmodel
                            .formatTime(it.transaction.time, "hh:mm a")
                    }
                )
            }
        }
    }
}