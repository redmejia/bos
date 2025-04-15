package com.bitinovus.bos.presentaion.viewmodels.appsnack

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class AppSnack(private val snack: Snack) : SnackbarVisuals {

    override val actionLabel: String?
        get() = when (snack.type) {
            SnackType.ERROR -> snack.type.name
            SnackType.SUCCESS -> snack.type.name
            SnackType.WARNING -> snack.type.name
        }

    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short

    override val message: String
        get() = when (snack.type) {
            SnackType.ERROR -> snack.message
            SnackType.SUCCESS -> snack.message
            SnackType.WARNING -> snack.message
        }
    override val withDismissAction: Boolean
        get() = false

}