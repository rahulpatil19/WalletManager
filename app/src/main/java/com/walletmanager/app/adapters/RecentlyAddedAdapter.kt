package com.walletmanager.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walletmanager.app.R
import com.walletmanager.app.domain.Transaction

class RecentlyAddedAdapter:
    ListAdapter<Transaction, RecentlyAddedAdapter.RecentlyAddedViewHolder>(TransactionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyAddedViewHolder {
        return RecentlyAddedViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecentlyAddedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecentlyAddedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val amountTv: TextView = itemView.findViewById(R.id.tv_amount)
        val categoryTv: TextView = itemView.findViewById(R.id.tv_category)

        fun bind(dbTransaction: Transaction){
            amountTv.text = dbTransaction.amount.toString()
            categoryTv.text = dbTransaction.category
        }

        companion object{
            fun create(parent: ViewGroup) : RecentlyAddedViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_recently_added, parent, false)
                return RecentlyAddedViewHolder(view)
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
