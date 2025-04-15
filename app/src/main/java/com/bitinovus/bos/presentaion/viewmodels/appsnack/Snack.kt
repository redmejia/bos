package com.bitinovus.bos.presentaion.viewmodels.appsnack

data class Snack(
    val message: String = "",
    val type: SnackType = SnackType.SUCCESS,
)
