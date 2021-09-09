package com.patilapps.walletmanager.screens.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.databinding.BottomSheetDeleteBinding
import com.patilapps.walletmanager.domain.Transaction
import com.patilapps.walletmanager.domain.asDbTransaction
import com.patilapps.walletmanager.screens.monthlydata.MonthDataFragment
import com.patilapps.walletmanager.utils.formatDateWithYear

class DeleteBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDeleteBinding

    private lateinit var deleteTransactionViewModel: DeleteTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = BottomSheetDeleteBinding.inflate(inflater, container, false)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        val args = arguments
        val transaction = args?.getParcelable<Transaction>("transaction")

        val deleteTransactionViewModelFactory =
            activity?.let {
                DeleteTransactionViewModelFactory(it.application,
                    walletDatabase.transactionDao)
            }

        deleteTransactionViewModel =
            ViewModelProvider(this.viewModelStore, deleteTransactionViewModelFactory!!).get(
                DeleteTransactionViewModel::class.java)

        transaction?.let {
            binding.tvTitle.text = it.title
            binding.tvDate.text = formatDateWithYear(it.date)
            binding.imageViewCategory.setImageDrawable(ResourcesCompat.getDrawable(resources,
                it.categoryIcon,
                resources.newTheme()))
            binding.imageViewCategory.drawable.setTint(ResourcesCompat.getColor(resources,
                it.categoryColor,
                resources.newTheme()))
        }

        binding.buttonDelete.setOnClickListener {
            val dbTransaction = transaction?.asDbTransaction()
            val deleteClickListener = parentFragment as DeleteClickListener
            deleteClickListener.onTransactionDelete(dbTransaction!!)
            dbTransaction?.let { deleteTransactionViewModel.deleteTransaction(it) }
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        deleteTransactionViewModel.deleteTransaction.observe(this.viewLifecycleOwner, { isDeleted ->
            if (isDeleted) {
                Toast.makeText(requireContext(), "Transaction deleted", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })

        return binding.root
    }


    companion object {
        fun newInstance(transaction: Transaction): DeleteBottomSheetFragment {
            val deleteFragment = DeleteBottomSheetFragment()
            val args = Bundle()
            args.putParcelable("transaction", transaction)
            deleteFragment.arguments = args
            return deleteFragment
        }
    }


    interface DeleteClickListener{
        fun onTransactionDelete(transaction: DbTransaction)
    }
}