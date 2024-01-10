package coba.gaes.horizontalcalendar

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarDateModel(var data: Date, var isCurrentDay: Boolean = false, var isSelected: Boolean = false) {
    val calendarDay: String get() = SimpleDateFormat("EE", Locale.ENGLISH).format(data)
    val calendarDate: String get() {
        val cal = Calendar.getInstance()
        cal.time = data
        val dateFormat = SimpleDateFormat("dd", Locale.ENGLISH)
        return dateFormat.format(data)
    }
}