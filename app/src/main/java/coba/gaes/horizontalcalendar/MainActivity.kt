package coba.gaes.horizontalcalendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), CalendarAdapter.OnDateClickListener {
    private lateinit var spinner: Spinner
    private lateinit var listDate: MutableList<CalendarDateModel>
    private lateinit var adapter: CalendarAdapter
    private lateinit var calendarTooltip: TextView
    private lateinit var calendarBar: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarBar = findViewById(R.id.calendar_bar)
        calendarBar.addItemDecoration(
            CalendarAdapter.ItemDecorator(
                resources.getDimensionPixelSize(
                    R.dimen.calendar_horizontal_margin
                ), resources.getDimensionPixelSize(R.dimen.calendar_vertical_margin)
            )
        )
        calendarBar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        listDate = getCalendar()
        adapter = CalendarAdapter(listDate, this)
        setupRecyclerView(adapter)

        spinner = findViewById(R.id.calendar_month)
        val list: Array<String> = arrayOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )

        val customAdapter = SpinnerAdapter(this, android.R.layout.simple_spinner_item, list)
        customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = customAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val monthOffset = position - getCurrentMonthIndex()
                listDate.clear()
                listDate = getCalendar(monthOffset)
                adapter = CalendarAdapter(listDate, this@MainActivity)
                setupRecyclerView(adapter)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun getCurrentMonthIndex(): Int {
        val currentMonth = SimpleDateFormat("MM", Locale.getDefault()).format(Calendar.getInstance().time)
        return currentMonth.toInt() - 1
    }

    private fun setupRecyclerView(adapter: CalendarAdapter) {
        val position = listDate.indexOfFirst { it.isCurrentDay }

        calendarBar.adapter = adapter
        if (position != -1) {
            calendarBar.scrollToPosition(position)
        }
    }

    private fun updateUi(data: Date) {
        val selectedDate = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).format(data)

        calendarTooltip = findViewById(R.id.calendar_tooltip)
        calendarTooltip.text = selectedDate
    }

    private fun getCalendar(monthOffset: Int = 0): MutableList<CalendarDateModel> {
        val calendar = Calendar.getInstance()

        if (monthOffset != 0) {
            calendar.add(Calendar.MONTH, monthOffset)
        }

        val numberOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentDate = Calendar.getInstance().time

        val dateList: MutableList<CalendarDateModel> = mutableListOf()

        for (i in 1..numberOfDaysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            val date = calendar.time
            val isCurrentDay = date == currentDate

            val calendarDateModel = CalendarDateModel(date, isCurrentDay)
            dateList.add(calendarDateModel)
        }
        return dateList
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDateClick(position: Int) {
        for (i in listDate.indices) {
            listDate[i].isSelected = (i == position)
        }

        Toast.makeText(this, "${listDate[position].data}", Toast.LENGTH_SHORT).show()
        updateUi(listDate[position].data)
        adapter.notifyDataSetChanged()
    }
}