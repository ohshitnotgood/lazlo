package io.github.praanto__samadder.cocoa.task_editor

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.databinding.ActivityTaskEditorBinding
import io.github.praanto__samadder.cocoa.fire.Auth.pushDocument
import io.github.praanto__samadder.cocoa.fire.Helpers.getIntentData
import io.github.praanto__samadder.cocoa.fire.Timestamps
import io.github.praanto__samadder.cocoa.fire.UserID.fetchUserID
import io.github.praanto__samadder.cocoa.model.*
import io.github.praanto__samadder.cocoa.receivers.TaskNotificationBroadcastReceiver
import io.github.praanto__samadder.cocoa.task_editor.PredefinedLists.getChemistryTopicsList
import io.github.praanto__samadder.cocoa.task_editor.PredefinedLists.getSessionsAdapter
import kotlinx.android.synthetic.main.activity_task_editor.*
import java.util.*

class TaskEditorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTaskEditorBinding
    private lateinit var passingData : IntentDataModel
    private lateinit var arrayList : ArrayList<String>

    /**
     * Private variable declarations
     */
    private lateinit var _chapterName: String
    private lateinit var _assignedDate: String
    private lateinit var _submissionDate: String
    private var _hasYearSwitchChecked: Boolean = false
    private lateinit var _year: String
    private lateinit var _session : String
    private lateinit var _pageIndex: String
    private lateinit var _documentId: String
    private lateinit var _exerciseTitle: String
    private lateinit var _details: String
    private lateinit var _variant : String

    private var _notificationId : Int = 0
    private var _flag : Int = Document.CREATE
    private var _collectionSize: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewData()
        initSessionsDropdownSpinner()

        val arrayAdapter = ArrayAdapter(this, R.layout.auto_complete_text_view_list_item, R.id.text1, arrayList)

        binding.autoChapterName.setAdapter(arrayAdapter)
        binding.autoChapterName.setDropDownBackgroundResource(R.drawable.auto_complete_dropdown_bg)

        binding.yearSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                mlMockDates_Parent.transitionToStart()
            } else {
                mlMockDates_Parent.transitionToEnd()
            }
        }

        binding.datePick1.setOnDateChangeListener { _, year, month, dayOfMonth ->
            updateAssignedDateString(year, month, dayOfMonth)
        }

        binding.datePick2.setOnDateChangeListener { _, year, month, dayOfMonth ->
            updateSubmissionDateString(year, month, dayOfMonth)
        }

        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

        binding.btnDeleteThisTask.setOnClickListener {
            updateVariables()
            saveTask(Document.DELETE)
            onBackPressed()
        }

        binding.btnSaveTask.setOnClickListener {
            updateVariables()
            if (!checkDataEntry()) return@setOnClickListener
            saveTask(_flag)
            onBackPressed()
        }

    }

    private fun initSessionsDropdownSpinner() {
        sessionPickerSpinner.setPopupBackgroundResource(R.drawable.spinner_dropdown_bg)
        sessionPickerSpinner.adapter = getSessionsAdapter(applicationContext)
    }

    private fun initializeViewData() {
        passingData = getIntentData(intent)


        when (passingData.fragmentName.toLowerCase(Locale.ROOT)) {
            "physics" -> arrayList = getChemistryTopicsList()
            "chemistry" -> arrayList = getChemistryTopicsList()
            "pure mathematics" -> arrayList = getChemistryTopicsList()
            "statistics" -> arrayList = getChemistryTopicsList()
        }

        passingData.taskDataModel.apply {
            Log.d("DocumentIdNotWorking", "Task editor class $documentId")
            _chapterName        = this.chapterName
            _exerciseTitle      = this.exerciseTitle
            _details            = this.details
            _pageIndex          = this.pageIndex

            _hasYearSwitchChecked = this.hasYearSwitchChecked
            _assignedDate       = this.assignedDate
            _submissionDate     = this.submissionDate

            _documentId         = this.documentId

            _session            = this.session
            _year               = this.year

            _variant            = this.variant
            _flag               = this.flag
        }

        _collectionSize = passingData.collectionSize

        binding.autoChapterName.setText(_chapterName)
        binding.exerciseTitle.setText(_exerciseTitle)
        binding.pageNumber.setText(_pageIndex)
        binding.details.setText(_details)


        if (_assignedDate != "Tap to set date" || _submissionDate != "Tap to set date") {
            binding.datePick1.date = Timestamps.convertToUnixTime(_assignedDate)
            binding.datePick2.date = Timestamps.convertToUnixTime(_submissionDate)
        }

        binding.yearSwitch.isChecked = _hasYearSwitchChecked
        if (_hasYearSwitchChecked) mlMockDates_Parent.transitionToEnd()


        binding.year.setText(_year)
        binding.variant.setText(_variant)


        binding.sessionPickerSpinner.post {
            binding.sessionPickerSpinner.setSelection(
                when (_session) {
                    Session.feb -> 1
                    Session.may -> 2
                    Session.nov -> 3
                    else -> 1
                }
            )
        }


        binding.assignedDateText.text = _assignedDate
        binding.submissionDateText.text = _submissionDate
    }

    private fun checkDataEntry () : Boolean {
        return if (_chapterName == "" && _assignedDate == "Tap to set date" && _submissionDate == "Tap to set date") {
            Toasty.warning(this, "You didn't enter title and assigned/submission dates").show()
            false
        } else if (Timestamps.convertToUnixTime(_submissionDate) < Timestamps.convertToUnixTime(_assignedDate)){
            Toasty.warning(this, "Submission Date must ba later than assigned date.").show()
            false
        } else true
    }

    private fun updateAssignedDateString(year: Int, month: Int, dayOfMonth: Int) {
        binding.assignedDateText.setTextColor(getColor(R.color.UIKitBlue))
        val stringDate = Timestamps.StringTime(dayOfMonth, month, year).date
        binding.assignedDateText.text = stringDate
    }

    private fun updateSubmissionDateString(year: Int, month: Int, dayOfMonth: Int) {
        binding.submissionDateText.setTextColor(getColor(R.color.UIKitBlue))
        val stringDate = Timestamps.StringTime(dayOfMonth, month, year).date
        binding.submissionDateText.text = stringDate
    }

    private fun saveTask(flag: Int) {
        var key = ""
        when (passingData.fragmentName.toLowerCase(Locale.ROOT)) {
            "physics" -> {
                key = SubjectKey.physics
                _notificationId = NotificationId.PHYSICS_CLASS_REMINDER
            }
            "chemistry" -> {
                key = SubjectKey.chemistry
                _notificationId = NotificationId.CHEMISTRY_CLASS_REMINDER
            }
            "pure maths" -> {
                key = SubjectKey.pureMathematics
                _notificationId = NotificationId.MATHS_CLASS_REMINDER
            }
            "statistics" -> {
                key = SubjectKey.statistics
                _notificationId = NotificationId.STATISTICS_CLASS_REMINDER
            }
        }

        pushDocument(
            key = key,
            chapterName = _chapterName,
            assignedDate = _assignedDate,
            submissionDate = _submissionDate,
            documentId = _documentId,
            flag = flag,
            details = _details,
            year = _year,
            hasYearSwitchChecked = _hasYearSwitchChecked,
            session = _session, variant = _variant, exerciseTitle = _exerciseTitle,
            pageIndex = _pageIndex, userUID = fetchUserID(application), collectionSize = _collectionSize
        )

        val intent = Intent(this, TaskNotificationBroadcastReceiver::class.java)
        intent.apply {
            putExtra("notificationDesc", "Your homework on $_chapterName is due tomorrow")
            putExtra("notificationId", _notificationId)
            putExtra("notificationTitle", "${passingData.fragmentName.capitalize(Locale.ROOT)} Homework reminder")
            putExtra("channelId", ChannelID.HOMEWORK_REMINDER)
        }

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance().apply {
            timeInMillis = Timestamps.convertToUnixTime(_submissionDate) - 86400000 - 63000
        }

        alarmManager.set(AlarmManager.RTC, calendar.timeInMillis, pendingIntent)

    }

    private fun updateVariables() {
        _chapterName = binding.autoChapterName.text.toString()
        _pageIndex = binding.pageNumber.text.toString()
        _details = binding.details.text.toString()
        _exerciseTitle = binding.exerciseTitle.text.toString()

        _session = binding.sessionPickerSpinner.selectedItem.toString()
        _variant = binding.variant.text.toString()
        _year = binding.year.text.toString()

        _assignedDate = binding.assignedDateText.text as String
        _submissionDate = binding.submissionDateText.text as String

        _hasYearSwitchChecked = binding.yearSwitch.isChecked
    }

}