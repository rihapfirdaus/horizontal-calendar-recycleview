package coba.gaes.horizontalcalendar

import android.content.res.ColorStateList
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(
    private val listDate: List<CalendarDateModel>,
    private val onDateClickListener: OnDateClickListener
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    interface OnDateClickListener {
        fun onDateClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarAdapter.CalendarViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.layout_date, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.CalendarViewHolder, position: Int) {
        val date = listDate[position]

        holder.calendarDate.text = date.calendarDate
        holder.calendarDay.text = date.calendarDay

        holder.layoutDate.setOnClickListener {
            onDateClickListener.onDateClick(position)
        }

        if (date.isCurrentDay) {
            holder.layoutDate.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            );
        } else if (date.isSelected) {
            holder.layoutDate.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.dark_gray
                )
            );
        } else {
            holder.layoutDate.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.gray
                )
            );
        }
    }

    override fun getItemCount(): Int {
        return listDate.size
    }

    class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val calendarDate: TextView = itemView.findViewById(R.id.calendar_date)
        val calendarDay: TextView = itemView.findViewById(R.id.calendar_day)
        val layoutDate: LinearLayout = itemView.findViewById(R.id.date_layout)
    }

    class ItemDecorator(private val horizontalSpacing: Int, private val verticalSpacing: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = verticalSpacing
            outRect.bottom = verticalSpacing
            outRect.left = horizontalSpacing
            outRect.right = horizontalSpacing
        }
    }
}