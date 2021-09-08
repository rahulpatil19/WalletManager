package com.patilapps.walletmanager.domain

data class Category(
    val name: String,
    val iconId: Int,
    val colorId: Int,
    val type: String = "default",
)
