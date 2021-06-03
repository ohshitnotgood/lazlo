package io.github.praanto__samadder.cocoa.custom_views

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.Month

class MonthRecyclerView constructor(private val month: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun getCurrentMonth() : Month {
        return Month.DECEMBER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 12
    }
}