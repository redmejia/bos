package com.bitinovus.bos.presentation.viewmodels.appsnack

data class Snack(
    val messageType: SnackMessageType = SnackMessageType.TRX_SUCCESS,
    val type: SnackStateType = SnackStateType.SUCCESS,
)
