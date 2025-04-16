package com.bitinovus.bos.presentaion.viewmodels.appsnack

enum class SnackStateType {
    SUCCESS,
    ERROR,
    WARNING
}

enum class SnackMessageType(val value: String) {
    TRX_SUCCESS("trx_successful"),
    ERROR_AMT("error_amount"),
    TRX_NO_ACT("no_action") // no action need exact total
}