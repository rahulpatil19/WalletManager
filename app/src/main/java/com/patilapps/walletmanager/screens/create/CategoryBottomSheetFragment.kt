package com.patilapps.walletmanager.screens.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.adapters.ChooseCategoryAdapter
import com.patilapps.walletmanager.domain.Category
import com.patilapps.walletmanager.utils.CategoryUtils

class CategoryBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var categoryAdapter: ChooseCategoryAdapter
    private lateinit var transactionType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionType = arguments?.getString("transaction_type") ?: "expense"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_category, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_category)

        categoryAdapter = ChooseCategoryAdapter { category ->
            val cateGoryClickListener = activity as CateGoryClickListener
            cateGoryClickListener.onCategoryClick(category)
            dismiss()
        }

        recyclerView.adapter = categoryAdapter

        if (transactionType.equals("income")) {
            categoryAdapter.categories = CategoryUtils.incomeCategories
        } else if (transactionType.equals("expense")) {
            categoryAdapter.categories = CategoryUtils.expenseCategories
        }

        return view
    }

    interface CateGoryClickListener {
        fun onCategoryClick(category: Category)
    }

  /*  override fun onAttachFragment(childFragment: Fragment) {
        try {
            cateGoryClickListener = context as CateGoryClickListener
        }
        catch (e: ClassCastException){
            throw ClassCastException("Need to implement bottom sheet interface")
        }
    }*/


    companion object {
        fun newInstance(transactionType: String): CategoryBottomSheetFragment {
            val bundle = Bundle()
            bundle.putString("transaction_type", transactionType)
            val categoryFragment = CategoryBottomSheetFragment()
            categoryFragment.arguments = bundle
            return categoryFragment
        }
    }
}