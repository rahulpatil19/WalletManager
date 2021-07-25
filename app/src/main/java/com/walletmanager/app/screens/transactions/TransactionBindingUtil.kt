package com.walletmanager.app.screens.transactions

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.walletmanager.app.R
import com.walletmanager.app.utils.formatDate
import java.util.*


@BindingAdapter("setTransactionAmount")
fun TextView.setTransactionAmount(amount: Long) {
    text = amount.toString()
}

@BindingAdapter("setTransactionColor")
fun TextView.setTransactionTextColor(type: String) {
    if (type.equals("income")) {
        setTextColor(ResourcesCompat.getColor(resources, R.color.green, resources.newTheme()))
    } else {
        setTextColor(ResourcesCompat.getColor(resources, R.color.red_1, resources.newTheme()))
    }
}

@BindingAdapter("setCategoryImage")
fun ImageView.setCategoryImage(iconId: Int?) {
    if (iconId != 0) {
        setImageDrawable(iconId?.let {
            ResourcesCompat.getDrawable(resources,
                it, resources.newTheme())
        })
    }
}

@BindingAdapter("setDate")
fun TextView.setDate(date: Date) {
    text = formatDate(date)
}
