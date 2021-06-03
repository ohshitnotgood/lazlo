package io.github.praanto__samadder.cocoa.new_record

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.dmoral.toasty.Toasty
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.fire.Auth.pushDocument
import io.github.praanto__samadder.cocoa.fire.Timestamps
import io.github.praanto__samadder.cocoa.fire.UserID.fetchUserID
import io.github.praanto__samadder.cocoa.model.Document
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.activity_record.view.*
import java.util.*

/**
 * This activity is used to add new tasks record to the cloud or edit a previously saved
 * task and save to the cloud.
 *
 * This activity is started from any of the subject fragments.
 *
 * Removed MVVM and DataBinding because [rootLayout].transitionToEnd() won't work with it.
 */

class NewRecordActivity : AppCompatActivity() {
//
//    private lateinit var userUID: String
//    private lateinit var fragmentName: String
//    private lateinit var title : String
//    private lateinit var dateAssigned : String
//    private lateinit var submissionDate : String
//    private lateinit var documentId : String
//
//    private var isDialogInShow = false
//    private var isDatePickerInShow = false
//
//    private lateinit var viewModel: NewRecordActivityViewModel
//    private lateinit var viewModelFactory : NewRecordActivityViewModelFactory
//
//    private var flag = Document.CREATE
//    private var collectionSize = 0
//    private val isComplete = MutableLiveData(false)
//
//    private val isCompleteObserver = Observer<Boolean> { onIsCompleteChange() }
//
//    private fun onIsCompleteChange() {
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_record)
//
//        val application = requireNotNull(application)
//
//        viewModelFactory = NewRecordActivityViewModelFactory(application)
//        viewModel = ViewModelProvider(this, viewModelFactory)[NewRecordActivityViewModel::class.java]
//
//        userUID = fetchUserID(application)
//
//        /**
//         * Change height of layout containing data fields to fit beneath the top bar.
//         */
//        val parentHeight = rootLayout.height
//        val topBarHeight = topHeader.height
//        dataFieldParent.layoutParams.height = parentHeight - topBarHeight
//
//        title = intent.getStringExtra("tasksTitle")!!
//        dateAssigned = intent.getStringExtra("dateAssigned")!!
//        submissionDate = intent.getStringExtra("submissionDate")!!
//
//        fragmentName = intent.getStringExtra("fragmentName")!!
//        documentId = intent.getStringExtra("documentId")!!
//
//        collectionSize = intent.getIntExtra("collectionSize", 0)
//
//        flag = intent.getIntExtra("status", Document.CREATE)
//
//        if (flag == Document.CREATED) {
//            // Hide Mark as done and Delete button
//            date1.setTextColor(Color.parseColor("#8C8C8C"))
//            date2.setTextColor(Color.parseColor("#8C8C8C"))
//
////            buttonSubmitText.visibility = View.GONE
////            buttonSubmitText.isEnabled = false
//
//            btnDeleteTask.visibility = View.GONE
//            btnDeleteTask.isEnabled = false
//        }
//
//        isComplete.observeForever(isCompleteObserver)
//
//        et_title.setText(title)
//        date1.text = dateAssigned
//        date2.text = submissionDate
//
//        viewModel.date1.value = datePickerView.date
//        viewModel.date2.value = datePickerView.date
//
//        viewModel.isSavingData.observeForever {
//            if (viewModel.isSavingData.value == true) {
//                Log.d("save task log", "Data saving")
//            } else {
//                Log.d("save task log", "Data saved")
//            }
//        }
//
//        var focus = true
//
//        datePickerView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            if (focus) {
//                date1.setTextColor(Color.WHITE)
//                val date = Timestamps.StringTime(dayOfMonth, month, year).date
//                date1.text = date
//                viewModel.date1.value = Timestamps.convertToUnixTime(date)
//            } else {
//                date2.setTextColor(Color.WHITE)
//                val date = Timestamps.StringTime(dayOfMonth, month, year).date
//                date2.text = date
//                viewModel.date2.value = Timestamps.convertToUnixTime(date)
//            }
//        }
//
//        btnCancel.setOnClickListener {
//            onBackPressed()
//        }
//
//        btnSaveTask.setOnClickListener {
//            val name = et_title.text.toString()
//            val dateAss = date1.text.toString()
//            val dateSub = date2.text.toString()
//            if (name != "" && dateAss != "Tap to set date." && dateSub != "Tap to set date.") {
//                saveTask(fragmentName, name, dateAss, dateSub, flag)
//            } else {
//                Toasty.warning(baseContext, "You gotta fill all fields first").show()
//            }
//        }
//
//        btnDeleteTask.setOnClickListener{
//            Toast.makeText(baseContext, "Delete button clicked", Toast.LENGTH_SHORT).show()
//            isDialogInShow = true
//            dialogBoxContainer.transitionToEnd()
//            backgroundTint.isClickable = true
//
//            dbPositive.setOnClickListener {
//                backgroundTint.isClickable = false
//                isDialogInShow = false
//                saveTask(fragmentName, title, dateAssigned, submissionDate, Document.DELETE)
//            }
//
//            dbNegative.setOnClickListener {
//                dialogBoxContainer.transitionToStart()
//                backgroundTint.isClickable = false
//                isDialogInShow = false
//            }
//        }
//
//        assignedFieldBackground.setOnClickListener {
//            datePickerView.date = viewModel.date1.value!!
//            focus = true
//            isDatePickerInShow = true
//            rootLayout.transitionToEnd()
//        }
//
//        submissionFieldBackground.setOnClickListener {
//            datePickerView.date = viewModel.date2.value!!
//            focus = false
//            isDatePickerInShow = true
//            rootLayout.transitionToEnd()
//        }
//
//        et_title.setOnClickListener {
//            if (!isDatePickerInShow) {
//                rootLayout.transitionToStart()
//                isDatePickerInShow = false
//            }
//        }
//
//        dataFieldParent.setOnClickListener {
//            et_title.clearFocus()
//            rootLayout.transitionToStart()
//            isDatePickerInShow = false
//        }
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        isComplete.removeObserver(isCompleteObserver)
//    }
//
//
//    override fun onBackPressed() {
//        if (!isDialogInShow) {
//            super.onBackPressed()
//        } else if (isDatePickerInShow) {
//            isDatePickerInShow = false
//            rootLayout.transitionToStart()
//        } else if (!isDatePickerInShow) {
//            super.onBackPressed()
//        }
//    }
//    /**
//     * Actions when the user presses the Save button.
//     */
//    private fun saveTask(fragmentName: String, name: String, dateAss: String, dateSub: String, status: Int) {
//        savingDialogue.visibility = View.VISIBLE
//        Log.d("save task log", "Started saving")
//
////        when (fragmentName.toLowerCase(Locale.ROOT)) {
////            "physics"           -> pushDocument("_phy", chapterName = name, assignedDate = dateAss, submissionDate = dateSub, userUID = userUID,
////                collectionSize = collectionSize, documentId = documentId, exerciseTitle = "", pageIndex = "", details = "", flag = status)
////            "chemistry"         -> pushDocument("_chem", chapterName = name, assignedDate = dateAss, submissionDate = dateSub, userUID = userUID,
////                collectionSize = collectionSize, documentId = documentId, exerciseTitle = "", pageIndex = "", details = "", flag = status)
////            "pure maths"        -> pushDocument("_p3", chapterName = name, assignedDate = dateAss, submissionDate = dateSub, userUID = userUID,
////                collectionSize = collectionSize, documentId = documentId, exerciseTitle = "", pageIndex = "", details = "",flag = status)
////            "statistics"        -> pushDocument("_stats", chapterName = name, assignedDate = dateAss, submissionDate = dateSub, userUID = userUID,
////                collectionSize = collectionSize, documentId = documentId, exerciseTitle = "", pageIndex = "", details = "", flag = status)
////        }
//        onTaskSaved()
//    }
//
//    /**
//     * Actions after the data is saved to Firebase.
//     */
//    private fun onTaskSaved() {
//        savingDialogue.visibility = View.GONE
//        onBackPressed()
//    }
}