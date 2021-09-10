package com.patilapps.walletmanager.screens.statistic

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.databinding.ListItemHistoryBinding
import com.patilapps.walletmanager.databinding.ListItemStatisticsBinding
import com.patilapps.walletmanager.domain.Statistics
import com.patilapps.walletmanager.domain.Transaction
import com.patilapps.walletmanager.utils.formatDate
import com.patilapps.walletmanager.utils.formatSelectedDate
import com.patilapps.walletmanager.utils.formatStringDate

class StatisticAdapter(val currency: String, val clickListener: (Statistics) -> Unit) :
    ListAdapter<Statistics, StatisticAdapter.StatisticsViewHolder>(
        StatisticsDiffUtil) {

    class StatisticsViewHolder private constructor(
        val binding: ListItemStatisticsBinding,
        val context: Context,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(statistic: Statistics, currency: String, clickListener: (Statistics) -> Unit) {
            binding.tvMonthTitle.text = statistic.month
            binding.tvMonthAmount.text = statistic.totalAmount.toString()
            binding.tvMonthTotalExpenseValue.text = statistic.totalExpense.toString()
            binding.tvMonthTotalIncomeValue.text = statistic.totalIncome.toString()
            binding.tvMonthTotalTransactions.text =
                context.getString(R.string.format_total_transactions,
                    statistic.totalTransactions)
            binding.tvMonthLastModifiedDate.text = statistic.lastModified
            binding.tvCurrency.text = currency
            binding.tvMonthIncomeCurrency.text = currency
            binding.tvMonthExpenseCurrency.text = currency
            binding.tvMonthLastModifiedDate.text = context.getString(R.string.getLastModifiedDate,
                formatStringDate(statistic.lastModified))

            Log.d("DATE", statistic.lastModified)

            binding.root.setOnClickListener {
                clickListener(statistic)
            }
        }

        companion object {
            fun create(parent: ViewGroup): StatisticsViewHolder {
                val binding = ListItemStatisticsBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                return StatisticsViewHolder(binding, parent.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        return StatisticsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, currency, clickListener)
    }
}

object StatisticsDiffUtil : DiffUtil.ItemCallback<Statistics>() {
    override fun areItemsTheSame(oldItem: Statistics, newItem: Statistics): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Statistics, newItem: Statistics): Boolean {
        return oldItem == newItem
    }
}