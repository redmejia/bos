package com.bitinovus.bos.presentation.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bitinovus.bos.presentation.components.card.CardContainer
import com.bitinovus.bos.presentation.components.card.HistoryCard
import com.bitinovus.bos.presentation.ui.theme.PrimaryGrayBase80
import com.bitinovus.bos.presentation.viewmodels.historyviewmodel.HistoryViewmodel
import com.bitinovus.bos.R
import com.bitinovus.bos.presentation.ui.theme.PrimaryBlue60
import com.bitinovus.bos.presentation.ui.theme.PrimaryRed00
import com.bitinovus.bos.presentation.ui.theme.PrimaryWhite90
import com.bitinovus.bos.presentation.ui.theme.PrimaryYellow00

@Composable
fun History(
    historyViewmodel: HistoryViewmodel,
) {
    val history by historyViewmodel.orderHistoryState.collectAsState()
    val isWriting by historyViewmodel.reportWriteState.collectAsState() // loader


    var isDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(isWriting) {
        if (!isWriting) {
            isDialogOpen = false
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(end = 4.dp, top = 4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = PrimaryBlue60
                    ),
                    onClick = { isDialogOpen = true }
                ) {
                    Row {
                        Text(stringResource(id = R.string.generate))
                        Icon(
                            painter = painterResource(id = R.drawable.edit_square),
                            contentDescription = "report"
                        )
                    }
                }
            }
        }
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
        Dialog(onDismissRequest = {
            // prevent to close when is writing a report file and user try to click outside of card
            isDialogOpen = isWriting // false default value
            // isDialogOpen = false
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterHorizontally),
                            imageVector = Icons.Default.Warning,
                            contentDescription = "report",
                            tint = PrimaryYellow00
                        )
                        Text(
                            text = stringResource(id = R.string.warning_message),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                        Row(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                enabled = !isWriting,
                                onClick = { historyViewmodel.writeReport() }
                            ) {
                                Text(stringResource(id = R.string.generate), color = PrimaryBlue60)
                            }
                            TextButton(
                                enabled = !isWriting,
                                onClick = { isDialogOpen = false }
                            ) {
                                Text(stringResource(id = R.string.cancel), color = PrimaryRed00)
                            }
                        }
                    }
                    if (isWriting) {
                        Box(
                            modifier = Modifier
                                .background(color = PrimaryWhite90)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(64.dp),
                                color = PrimaryBlue60,
                                trackColor = PrimaryGrayBase80,
                            )
                        }
                    }
                }
            }
        }
    }
}


