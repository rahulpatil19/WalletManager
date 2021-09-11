package com.patilapps.walletmanager.screens.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.patilapps.walletmanager.databinding.ListItemMonthBinding
import com.patilapps.walletmanager.domain.Transaction


class SearchAdapter(
    val currency: String,
    val selectedTransaction: (Transaction, String) -> Unit,
) :
    ListAdapter<Transaction, SearchAdapter.TransactionViewHolder>(TransactionDiffUtil) {

    class TransactionViewHolder private constructor(
        val binding: ListItemMonthBinding,
        val selectedTransaction: (Transaction, String) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction, currency: String) {
            binding.transaction = transaction
            binding.currency = currency
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                selectedTransaction(transaction, "edit")
            }

            binding.buttonDelete.setOnClickListener {
                selectedTransaction(transaction, "delete")
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                selectedTransaction: (Transaction, String) -> Unit,
            ): TransactionViewHolder {
                val binding =
                    ListItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TransactionViewHolder(binding, selectedTransaction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.create(parent, selectedTransaction)
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