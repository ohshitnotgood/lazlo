package io.github.praanto__samadder.cocoa.fragments.chemistry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.databinding.FragmentSubjectsBinding
import io.github.praanto__samadder.cocoa.fire.Auth
import io.github.praanto__samadder.cocoa.fire.Auth.fetchDocument
import io.github.praanto__samadder.cocoa.fire.UserID.fetchUserID
import io.github.praanto__samadder.cocoa.model.*
import kotlinx.coroutines.*

/**
 * ChemistryFragment class.
 *
 * Contains the CardView for the records.
 * @author Praanto Samadder
 */

class ChemistryFragment : Fragment(), TaskHolderInterface {

    private lateinit var binding: FragmentSubjectsBinding
    private lateinit var recyclerViewAdapter: TasksRecyclerViewAdapter
    private lateinit var taskDataModelList: ArrayList<TaskDataModel>
    private var collectionSize: Int = 0

    private val ioScope = CoroutineScope(Dispatchers.IO)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_subjects, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ioScope.cancel()
    }

    override fun initRecyclerView() {
        val tasksRecyclerViewAdapter = TasksRecyclerViewAdapter(taskDataModelList, getString(R.string.bnChem), collectionSize)
        recyclerViewAdapter = tasksRecyclerViewAdapter
        binding.tasksRecyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@ChemistryFragment.context)
        }

        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition - 1

                taskDataModelList[position].apply {
                    taskDataModelList.removeAt(position)
                    binding.tasksRecyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)

                    Auth.pushDocument(key = SubjectKey.chemistry, chapterName = chapterName, assignedDate = assignedDate,
                        submissionDate = submissionDate, userUID = fetchUserID(requireActivity().application),
                        collectionSize = collectionSize, documentId = documentId,
                        flag = Document.DELETE, exerciseTitle = exerciseTitle, pageIndex = pageIndex, details = details,
                        hasYearSwitchChecked = hasYearSwitchChecked, year = year, session = session, variant = variant
                    )


                    Log.d("ArrayListDebug", "List size: " + taskDataModelList.size.toString() + "; adapter position: " +
                            viewHolder.adapterPosition.toString())

                    if (taskDataModelList.size == 0) {
                        tasksRecyclerViewAdapter.taskDataModelList = taskDataModelList
                        binding.tasksRecyclerView.adapter = tasksRecyclerViewAdapter
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.tasksRecyclerView)
    }

    override fun removeProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.tasksRecyclerView.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tasksRecyclerView.visibility = View.GONE
    }

    override fun fetchData() {
        super.fetchData()
        ioScope.launch {
            val application = requireActivity().application
            val chemistryDocumentList =  fetchDocument(fetchUserID(application), SubjectKey.chemistry)
            taskDataModelList = chemistryDocumentList.list
            collectionSize = chemistryDocumentList.documentSize
            withContext(Dispatchers.Main) {
                super.onDataFetched()
            }
        }
    }
}