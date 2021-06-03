package io.github.praanto__samadder.cocoa.new_record

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


/**
 * ViewModel class for NewRecordActivity class
 */

class NewRecordActivityViewModel (application: Application) : AndroidViewModel(application) {

    /**
     * Set to true if FOCUS is on Date Assigned
     *
     * Set to false if FOCUS is on Date Submissions
     */
    var focus = MutableLiveData(DATE_ASSIGN)

    val date1 = MutableLiveData<Long>()
    val date2 = MutableLiveData<Long>()

    /**
     * Callback function when DateAssigned field is clicked
     */
    fun onDateAssignLayoutClick () {
        focus.value = DATE_ASSIGN
    }

    val isSavingData = MutableLiveData(false)

    /**
     * Callback function when DateSubmit field is clicked
     */
    fun onDateSubmitLayoutClick () {
        focus.value = DATE_SUBMISSION
    }

    companion object {
        private const val DATE_ASSIGN = true
        private const val DATE_SUBMISSION = false
    }
}