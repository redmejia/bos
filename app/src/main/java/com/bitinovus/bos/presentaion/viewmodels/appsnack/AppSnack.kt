package com.bitinovus.bos.presentaion.viewmodels.appsnack

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class AppSnack(private val snack: Snack) : SnackbarVisuals {

    override val actionLabel: String?
        get() = when (snack.type) {
            SnackStateType.ERROR -> snack.type.name
            SnackStateType.SUCCESS -> snack.type.name
            SnackStateType.WARNING -> snack.type.name
        }

    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short

    override val message: String
        get() = when (snack.messageType) {
            SnackMessageType.ERROR_AMT -> snack.messageType.value
            SnackMessageType.TRX_SUCCESS -> snack.messageType.value
            SnackMessageType.TRX_NO_ACT -> snack.messageType.value
        }
    override val withDismissAction: Boolean
        get() = false

}