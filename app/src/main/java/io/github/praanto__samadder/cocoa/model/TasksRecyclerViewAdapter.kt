package io.github.praanto__samadder.cocoa.model

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.fire.Helpers.setIntentData
import io.github.praanto__samadder.cocoa.fire.Helpers.vibrate
import io.github.praanto__samadder.cocoa.task_editor.TaskEditorActivity
import kotlinx.android.synthetic.main.recycler_view_no_items.view.*
import kotlinx.android.synthetic.main.recycler_view_page_title.view.*
import kotlinx.android.synthetic.main.recycler_view_top_card.view.*
import java.util.*

/**
 * TasksRecyclerViewAdapter class is initialised inside the OnDataFetched function from DashboardActivity after data in loaded from Firebase.
 *
 * Has two forms of items.
 * + The Title card item.
 * + The Task card item.
 * @param taskDataModelList ArrayList of tasks objects
 * @param fragmentName Fragment title (large yellow text)
 * @author Praanto Samadder
 */
class TasksRecyclerViewAdapter (
    var taskDataModelList: ArrayList<TaskDataModel>,
    private val fragmentName: String, private val collectionSize: Int
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isTitleAdded = false

    inner class TitleCardViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tabTitle : TextView = itemView.tvTabTitle

        init {
            itemView.btnAddNewRecord.setOnClickListener {
                vibrate(it.context)
                val intent = Intent(itemView.context, TaskEditorActivity::class.java)

                setIntentData(intent = intent,
                    chapterName = "",
                    hasYearSwitchChecked = false,
                    year = "",
                    session = Session.feb,
                    variant = "",
                    assignedDate = "Tap to set date",
                    submissionDate = "Tap to set date",
                    fragmentName = fragmentName,
                    documentId = "",
                    exerciseTitle = "",
                    details = "",
                    pageIndex = "",
                    collectionSize = collectionSize,
                    flag = Document.CREATE
                )

                itemView.context.startActivity(intent)
            }
        }

        fun bind(tabName: String) {
            tabTitle.text = tabName
        }
    }

    inner class ItemBindViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val rvItemTitle : TextView = itemView.cellTitle
        private val rvItemSubtitle : TextView = itemView.cellSubtitle

        init {

            itemView.setOnClickListener {
                vibrate(it.context)

                val intent = Intent(itemView.context, TaskEditorActivity::class.java)
                val position = adapterPosition - 1

                taskDataModelList[position].apply {
                    Log.d("DocumentIdNotWorking", "Adapter $documentId")
                    setIntentData(intent = intent,
                        chapterName = chapterName,
                        exerciseTitle = exerciseTitle,
                        pageIndex = pageIndex,
                        details = details,
                        year = year,
                        variant = variant,
                        hasYearSwitchChecked = hasYearSwitchChecked,
                        session = session,
                        assignedDate = assignedDate,
                        submissionDate = submissionDate,
                        fragmentName = fragmentName.toLowerCase(Locale.ROOT),
                        documentId = documentId,
                        collectionSize = collectionSize,
                        flag = Document.UPDATE
                    )
                }

                it.context.startActivity(intent)
            }
        }

        fun bind(title: String, submissionDate: String) {
            rvItemTitle.text = title
            rvItemSubtitle.text = submissionDate
        }
    }

    inner class NoItemsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tabTitle : TextView = itemView.tabTitleNoTasks
        private val newTaskButton = itemView.btnAddNewRecordNoTitle

        init {
            newTaskButton.setOnClickListener {
                vibrate(it.context)
                val intent = Intent(itemView.context, TaskEditorActivity::class.java)

                setIntentData(intent = intent,
                    chapterName = "",
                    year = "",
                    variant = "",
                    session = Session.feb,
                    hasYearSwitchChecked = false,
                    details = "",
                    assignedDate = "Tap to set date",
                    submissionDate = "Tap to set date",
                    documentId = "",
                    exerciseTitle = "",
                    pageIndex = "",
                    fragmentName = fragmentName,
                    collectionSize = collectionSize,
                    flag = Document.CREATE
                )

                it.context.startActivity(intent)
            }
        }

        /** Binds string parameters to TextViews of the view items.
         * @param title Title of a task. Displayed as the title of the card.
         */
        fun bind(title: String) {
            tabTitle.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (itemCount == 1) {
            NoItemsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_no_items, parent, false)
            )
        } else {
            if (isTitleAdded) {
                ItemBindViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_top_card, parent, false)
                )
            } else {
                TitleCardViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_page_title, parent, false)
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Position 1 is the yellow title and
        // the first item is the list is position 2. So, in order to make the first item in the list behave like it
        // was position 1, the position value is decremented.
        val p = position - 1
        when (holder) {
            is TitleCardViewHolder -> {
                holder.bind(fragmentName)
                isTitleAdded = true
            }
            is ItemBindViewHolder -> {
                holder.bind(taskDataModelList[p].chapterName, taskDataModelList[p].submissionDate)
            }
            is NoItemsViewHolder -> {
                holder.bind(fragmentName)
            }
        }
    }

    override fun getItemCount(): Int {
        return taskDataModelList.size + 1   // One is added because the title layout was not included in the list.
    }
}