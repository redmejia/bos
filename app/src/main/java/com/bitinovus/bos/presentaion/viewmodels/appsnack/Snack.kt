package com.bitinovus.bos.presentaion.viewmodels.appsnack

data class Snack(
    val messageType: SnackMessageType = SnackMessageType.TRX_SUCCESS,
    val type: SnackStateType = SnackStateType.SUCCESS,
)
