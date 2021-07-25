package com.walletmanager.app.screens.wallet

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walletmanager.app.domain.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WalletAdapter : ListAdapter<WalletDataItem, RecyclerView.ViewHolder>(WalletDiffUtil) {

    val job = Job()

    val scope = CoroutineScope(Dispatchers.Main + job)

    fun addHeaderAndSubmitList(walletList: List<Transaction>){
        var month: String
        scope.launch {
            for ((index, transaction) in walletList.withIndex()){
                val currentMonth = transaction.month
                month = transaction.month
            }
        }

    }

    class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}


sealed class WalletDataItem(){

   open val headerString: String  = "Jan"

    class WalletTransaction: WalletDataItem(){
       override val headerString = "sdf"
    }


}

object WalletDiffUtil : DiffUtil.ItemCallback<WalletDataItem>(){
    override fun areItemsTheSame(oldItem: WalletDataItem, newItem: WalletDataItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: WalletDataItem, newItem: WalletDataItem): Boolean {
        TODO("Not yet implemented")
    }

}