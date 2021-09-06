package com.walletmanager.app.screens.monthlydata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.walletmanager.app.databinding.ListItemAllTransactionBinding
import com.walletmanager.app.databinding.ListItemMonthBinding
import com.walletmanager.app.domain.Transaction

class MonthDataAdapter(val currency: String) :
    ListAdapter<Transaction, MonthDataAdapter.TransactionViewHolder>(TransactionDiffUtil) {

    class TransactionViewHolder private constructor(val binding: ListItemMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction, currency: String) {
            binding.transaction = transaction
            binding.currency = currency
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): TransactionViewHolder {
                val binding =
                    ListItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TransactionViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position), currency)
    }
}

private object TransactionDiffUtil : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }

}