package com.walletmanager.app.screens.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.walletmanager.app.databinding.ListItemAllTransactionBinding
import com.walletmanager.app.database.entities.DbTransaction
import com.walletmanager.app.databinding.ListItemMonthHeaderBinding
import java.lang.ClassCastException

private const val VIEW_TYPE_HEADER = 1
private const val VIEW_TYPE_DATA = 2

class AllTransactionAdapter :
    PagingDataAdapter<TransactionModel, RecyclerView.ViewHolder>(
        TransactionDiffUtil) {

    class TransactionViewHolder(val binding: ListItemAllTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dbTransaction: DbTransaction) {
            binding.transaction = dbTransaction
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): TransactionViewHolder {
                val binding =
                    ListItemAllTransactionBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                return TransactionViewHolder(binding)
            }
        }
    }

    class HeaderViewHolder(val binding: ListItemMonthHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(monthWithYear: String) {
            binding.tvHeaderMonth.text = monthWithYear
        }

        companion object {
            fun create(parent: ViewGroup): HeaderViewHolder {
                val binding =
                    ListItemMonthHeaderBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                //   val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_recently_added, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> TransactionViewHolder.create(parent)
            VIEW_TYPE_HEADER -> HeaderViewHolder.create(parent)
            else -> throw ClassCastException("Unknown ViewHolder class")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> {
                val transactionItem = getItem(position) as TransactionModel.TransactionData
                holder.bind(transactionItem.transaction)
            }
            is HeaderViewHolder -> {
                val monthItem = getItem(position) as TransactionModel.HeaderData
                holder.bind(monthItem.monthWithYear)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item as TransactionModel) {
            is TransactionModel.HeaderData -> VIEW_TYPE_HEADER
            is TransactionModel.TransactionData -> VIEW_TYPE_DATA
        }
    }

    /*  fun addHeaderAndSubmitList(pagingData: PagingData<DbTransaction>) {
          adapterScope.launch {
              for (item in pagingData) {
                  val newMonth = item.month
                  val oldMonth = previousMonth
                  previousMonth = newMonth
                  if(newMonth != oldMonth){
                     dataItemList.add(DataItem.HeaderItem(newMonth))
                  }
                  else{
                      dataItemList.add(DataItem.TransactionItem(item))
                  }
              }
              withContext(Dispatchers.Main) {
                  submitData(PagingData.from(dataItemList))
              }
          }
      }*/
}

object TransactionDiffUtil : DiffUtil.ItemCallback<TransactionModel>() {
    override fun areItemsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean {
        return oldItem == newItem
    }
}

sealed class TransactionModel {

    data class TransactionData(val transaction: DbTransaction) : TransactionModel()

    data class HeaderData(val monthWithYear: String) : TransactionModel()
}
