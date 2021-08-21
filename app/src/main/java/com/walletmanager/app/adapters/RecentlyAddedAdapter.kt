package com.walletmanager.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walletmanager.app.R
import com.walletmanager.app.databinding.ListItemRecentlyAddedBinding
import com.walletmanager.app.domain.Transaction

class RecentlyAddedAdapter :
    ListAdapter<Transaction, RecentlyAddedAdapter.RecentlyAddedViewHolder>(TransactionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyAddedViewHolder {
        return RecentlyAddedViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecentlyAddedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecentlyAddedViewHolder(val binding: ListItemRecentlyAddedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.transaction = transaction
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): RecentlyAddedViewHolder {
                val binding =
                    ListItemRecentlyAddedBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                return RecentlyAddedViewHolder(binding)
            }
        }
    }
}

object TransactionDiffUtil : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

}
