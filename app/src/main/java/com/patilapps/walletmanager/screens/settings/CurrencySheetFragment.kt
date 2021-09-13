package com.patilapps.walletmanager.screens.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.databinding.ListItemCurrencyBinding
import com.patilapps.walletmanager.utils.WalletPreference

class CurrencySheetFragment : BottomSheetDialogFragment() {

    var currencyList = listOf<ExtendedCurrency>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sheet_currency, container, false)

        currencyList = ExtendedCurrency.getAllCurrencies()

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_currency)

        val adapter = CurrencyAdapter(requireContext()) { name, code, symbol, flagResourceId ->
            WalletPreference.storeCurrencyData(requireContext(), name, code, symbol, flagResourceId)
            Toast.makeText(requireContext(), "$name selected", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        recyclerView.adapter = adapter
        adapter.currencyList = currencyList

        val closeButton = view.findViewById<MaterialButton>(R.id.button_currency_close)
        closeButton.setOnClickListener {
            dismiss()
        }

        return view
    }

    class CurrencyAdapter(
        val context: Context,
        private val selectedCurrency: (String, String, String, Int) -> Unit,
    ) :
        RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
        var currencyList = listOf<ExtendedCurrency>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }


        class CurrencyViewHolder(
            val binding: ListItemCurrencyBinding,
            val selectedCurrency: (String, String, String, Int) -> Unit,
        ) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(item: ExtendedCurrency, context: Context) {
                val flagResourceId = item.flag
                val name = item.name
                val code = item.code
                val symbol = item.symbol

                binding.imageViewCountry.setImageResource(flagResourceId)
                binding.tvCurrencyName.text = name
                binding.tvCurrencyCode.text = code
                if (symbol.equals("0")) {
                    binding.tvCurrencySymbol.text = context.getString(R.string.ruppee_symbol)
                } else {
                    binding.tvCurrencySymbol.text = symbol
                }
                binding.root.setOnClickListener {
                    selectedCurrency(name,
                        code,
                        binding.tvCurrencySymbol.text.toString(),
                        flagResourceId)
                }
            }

            companion object {
                fun create(
                    parent: ViewGroup,
                    selectedCurrency: (String, String, String, Int) -> Unit,
                ): CurrencyViewHolder {
                    val binding =
                        ListItemCurrencyBinding.inflate(LayoutInflater.from(parent.context),
                            parent,
                            false)
                    return CurrencyViewHolder(binding, selectedCurrency)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
            return CurrencyViewHolder.create(parent = parent, selectedCurrency = selectedCurrency)
        }

        override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
            val item = currencyList.get(position)
            holder.bind(item, context)
        }

        override fun getItemCount(): Int {
            return currencyList.size
        }
    }
}