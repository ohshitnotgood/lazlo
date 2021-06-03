package io.github.praanto__samadder.cocoa.custom_views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.praanto__samadder.cocoa.R
import kotlinx.android.synthetic.main.activity_record.view.*
import kotlinx.android.synthetic.main.custom_date_picker_day.view.*

class DaysRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateTextView : TextView = itemView.custom_date_picker_text

        fun bind(day: String) {
            dateTextView.text = day
        }
    }


    private fun daysList() : ArrayList<String> {
        var i = 1
        val list = ArrayList<String> ()
        while (i <= 31) {
            list.add(i.toString())
            i++
        }
        return list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_date_picker_day, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(daysList()[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return daysList().size
    }

}