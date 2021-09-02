package com.walletmanager.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walletmanager.app.databinding.ListItemRecentlyAddedBinding
import com.walletmanager.app.domain.Transaction

class RecentlyAddedAdapter(val transaction: (transaction: Transaction)-> Unit) :
    ListAdapter<Transaction, RecentlyAddedAdapter.RecentlyAddedViewHolder>(TransactionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyAddedViewHolder {
        return RecentlyAddedViewHolder.create(parent, transaction)
    }

    override fun onBindViewHolder(holder: RecentlyAddedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecentlyAddedViewHolder(val binding: ListItemRecentlyAddedBinding, val selectedTransaction : (transaction: Transaction)-> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.transaction = transaction
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                selectedTransaction(transaction)
            }
        }

        companion object {
            fun create(parent: ViewGroup, transaction: (transaction: Transaction) -> Unit): RecentlyAddedViewHolder {
                val binding =
                    ListItemRecentlyAddedBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                return RecentlyAddedViewHolder(binding, transaction)
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
