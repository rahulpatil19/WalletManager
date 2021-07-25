package com.walletmanager.app.screens.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.walletmanager.app.R
import java.util.*

class SelectDateBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_select_date, container, false)

        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)

        val selectDateBtn = view.findViewById<MaterialButton>(R.id.button_select_date)
        val cancelBtn = view.findViewById<MaterialButton>(R.id.button_cancel_date)

        selectDateBtn.setOnClickListener {
            val dateClickListener = activity as DateClickListener

            val calendar = Calendar.getInstance(TimeZone.getDefault())
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
            calendar.set(Calendar.MONTH, datePicker.month)
            calendar.set(Calendar.YEAR, datePicker.year)

            dateClickListener.onDateSelected(calendar)

            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }

        return view
    }

    interface DateClickListener{
        fun onDateSelected(calendar: Calendar)
    }
}