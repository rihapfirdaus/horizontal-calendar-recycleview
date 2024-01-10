package coba.gaes.horizontalcalendar

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class SpinnerAdapter(context: Context, resource: Int, objects: Array<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setBackgroundResource(R.color.dark_gray)
        view.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.setBackgroundResource(R.color.dark_gray)
        view.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        return view
    }
}
