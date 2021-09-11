package com.patilapps.walletmanager.utils

import android.view.View
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.domain.Transaction
import com.patilapps.walletmanager.screens.search.SearchAdapter
import com.patilapps.walletmanager.screens.search.SearchResult
import java.util.*

// Transaction List Bindings
@BindingAdapter(value = ["setAmount", "setColor"])
fun TextView.setTransactionAmount(amount: Long, type: String) {
    text = amount.toString()
    if (type.equals("income")) {
        setTextColor(ResourcesCompat.getColor(resources, R.color.green_1, resources.newTheme()))
    } else {
        setTextColor(ResourcesCompat.getColor(resources, R.color.red_1, resources.newTheme()))
    }
}

@BindingAdapter(value = ["setCurrency", "setTransactionSymbol"])
fun TextView.setCurrency(currency: String, type: String) {
    if (type == "income") {
        text = currency
        setTextColor(ResourcesCompat.getColor(resources, R.color.green_1, resources.newTheme()))
    } else {
        setTextColor(ResourcesCompat.getColor(resources, R.color.red_1, resources.newTheme()))
        text = context.getString(R.string.set_currency, "-$currency")
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


// Search Bindings

@BindingAdapter("android:setEmptyLayoutVisibility")
fun LinearLayout.setEmptyLayoutVisibility(result: SearchResult){
    visibility = when(result){
        is SearchResult.Loading -> View.GONE
        is SearchResult.Success -> View.GONE
        is SearchResult.Error -> View.VISIBLE
    }
}

@BindingAdapter("android:setSearchProgress")
fun ProgressBar.setProgressVisibility(result: SearchResult){
    visibility = when(result){
        is SearchResult.Loading -> View.VISIBLE
        is SearchResult.Success -> View.GONE
        is SearchResult.Error -> View.GONE
    }
}
