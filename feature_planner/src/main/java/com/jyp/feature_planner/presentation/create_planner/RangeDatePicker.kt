package com.jyp.feature_planner.presentation.create_planner

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import com.jyp.feature_planner.R

class RangeDatePicker {
    fun show(supportFragmentManager: FragmentManager, onPositiveButtonClickListener: (Long, Long) -> Unit) {
        MaterialDatePicker.Builder.dateRangePicker().apply {
            setTheme(R.style.MaterialRangeDatePickerStyle)
            val now = Calendar.getInstance()
            setTitleText("")
            setSelection(Pair(now.timeInMillis, now.timeInMillis))

            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)

            calendar.set(Calendar.YEAR, currentYear - 1)
            val start = calendar.timeInMillis

            calendar.set(Calendar.YEAR, currentYear + 2)
            val end = calendar.timeInMillis

            setCalendarConstraints(
                    CalendarConstraints.Builder().setStart(start).setEnd(end).build()
            )
        }.build().apply {
            addOnPositiveButtonClickListener {
                val startTimeStamp = it.first ?: return@addOnPositiveButtonClickListener
                val endTimeStamp = it.second ?: return@addOnPositiveButtonClickListener

                onPositiveButtonClickListener.invoke(startTimeStamp,endTimeStamp)
            }
        }.show(supportFragmentManager, "")
    }
}
