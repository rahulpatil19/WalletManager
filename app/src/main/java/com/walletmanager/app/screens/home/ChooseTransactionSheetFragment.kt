package com.walletmanager.app.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.walletmanager.app.R

class ChooseTransactionSheetFragment: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_choose_transaction, container, false)

        val tvIncome = view.findViewById<TextView>(R.id.tv_income)
        val tvExpense = view.findViewById<TextView>(R.id.tv_expense)

        tvIncome.setOnClickListener { v->
            findNavController().navigate(ChooseTransactionSheetFragmentDirections.
            actionChooseTransactionSheetFragmentToTransactionActivity("income"))
        }

        tvExpense.setOnClickListener { v->
            findNavController().navigate(ChooseTransactionSheetFragmentDirections
                .actionChooseTransactionSheetFragmentToTransactionActivity("expense"))
        }

        return view
    }
}