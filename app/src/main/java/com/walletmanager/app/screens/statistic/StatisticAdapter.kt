package com.walletmanager.app.screens.statistic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walletmanager.app.databinding.ListItemHistoryBinding
import com.walletmanager.app.domain.Transaction

class StatisticAdapter :
    ListAdapter<Transaction, StatisticAdapter.TransactionViewHolder>(
        TransactionDiffUtil) {

    class TransactionViewHolder(val binding: ListItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {

        }

        companion object {
            fun create(parent: ViewGroup): TransactionViewHolder {
                val binding = ListItemHistoryBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                return TransactionViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object TransactionDiffUtil : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        TODO("Not yet implemented")
    }
}