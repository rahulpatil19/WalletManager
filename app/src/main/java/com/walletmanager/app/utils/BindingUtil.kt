package com.walletmanager.app.utils

import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.walletmanager.app.R
import java.util.*

// Transaction List Bindings
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

@BindingAdapter("android:setCategoryImage")
fun ImageView.setCategoryImage(iconId: Int?) {
    if (iconId != 0) {
        setImageDrawable(ResourcesCompat.getDrawable(resources, iconId!!, resources.newTheme())
            /* AppCompatResources.getDrawable(context, iconId)*/)
    }
}

@BindingAdapter("setDate")
fun TextView.setDate(date: Date) {
    text = formatDate(date)
}

@BindingAdapter("android:setDateWithYear")
fun TextView.setDateWithYear(date: Date) {
    text = formatDateWithYear(date)
}

// Select category bindings...
@BindingAdapter("setCategoryBackgroundColor")
fun RelativeLayout.setCategoryBackgroundColor(colorId: Int) {
    background.setTint(ContextCompat.getColor(context,
        colorId))
}

@BindingAdapter("setCategoryIconColor")
fun ImageView.setCategoryIconColor(colorId: Int) {
    drawable.setTint(ResourcesCompat.getColor(resources,
        colorId,
        resources.newTheme()))
}

@BindingAdapter("setCategoryIcon")
fun ImageView.setCategoryIcon(iconId: Int) {
    setImageDrawable(AppCompatResources.getDrawable(context, iconId))
}