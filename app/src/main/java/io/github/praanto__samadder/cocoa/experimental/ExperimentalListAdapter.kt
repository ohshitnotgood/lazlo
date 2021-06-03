package io.github.praanto__samadder.cocoa.experimental

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.boilerplate.room.TasksDatabase
import io.github.praanto__samadder.cocoa.boilerplate.room.TasksEntity
import io.github.praanto__samadder.cocoa.model.H
import kotlinx.android.synthetic.main.recycler_view_top_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExperimentalListAdapter : PagingDataAdapter<TasksEntity, ListViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TasksEntity>() {
            override fun areContentsTheSame(oldItem: TasksEntity, newItem: TasksEntity) = oldItem.id == newItem.id

            override fun areItemsTheSame(oldItem: TasksEntity, newItem: TasksEntity) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.experimental_recycler_view_cell, parent, false),
            parent.context
        )
    }

    override fun onBindViewHolder(
        holder: ListViewHolder, position: Int)
    {
        Log.d(H.initTag, "onBindViewHolder")

        getItem(position)?.let {
            val tasksEntity : TasksEntity = it
            holder.bindTo(tasksEntity)
            Log.d(H.initTag, "bind to called inside OnBindViewHolder")
        }
    }
}

class ListViewHolder(val parent: View, val context: Context) : RecyclerView.ViewHolder (parent) {

    init {
        Log.d(H.initTag, "Called from ListViewHolder init class")
    }

    private val itemTitle = parent.cellTitle
    private var tasksEntity: TasksEntity? = null

    fun bindTo(tasksEntity: TasksEntity) {
        parent.setOnClickListener {
            val dao = Room.databaseBuilder(context, TasksDatabase::class.java, "tasksDatabase")
                .build()
                .TasksDao()

            CoroutineScope(Dispatchers.IO).launch {
                dao.updateTask(TasksEntity(tasksEntity.id, "Hello, Dorkshit Bangladesh"))
                withContext(Dispatchers.Main) {
                    itemTitle.text = context.getString(R.string.app_name)
                }
            }

        }

        Log.d(H.initTag, "Bind to function declared")
        this.tasksEntity = tasksEntity
        itemTitle.text = tasksEntity.name
    }
}
