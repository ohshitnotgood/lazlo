package io.github.praanto__samadder.cocoa.fire

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import io.github.praanto__samadder.cocoa.model.Document
import io.github.praanto__samadder.cocoa.model.H
import io.github.praanto__samadder.cocoa.model.IntentDataModel
import io.github.praanto__samadder.cocoa.model.TaskDataModel

object Helpers {

    /**
     * Tries a sharp "tap" vibration if supported by device. Otherwise, vibrates like normal for 100ms.
     */
    fun vibrate(application: Application) {
        val v = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        try {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_CLICK))
        } catch (e: Exception) {
            Log.e("VibratorService", e.message!!)
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    fun vibrate(context: Context) {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        try {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_CLICK))
        } catch (e: Exception) {
            Log.e("VibratorService", e.message!!)
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    class StringTime(_dayOfMonth: Int, _month: Int, _year: Int) {
        val day = "$_dayOfMonth "
        val month = when (_month + 1) {
            1  -> "January "
            2  -> "February "
            3  -> "March "
            4  -> "April "
            5  -> "May "
            6  -> "June "
            7  -> "July "
            8  -> "August "
            9  -> "September "
            10 -> "October "
            11 -> "November "
            12 -> "December "

            else -> "January, "
        }
        val year = "$_year"
        val date = day + month + year
    }



    fun getIntentData(intent: Intent) : IntentDataModel {
        intent.apply {
            val chapterName = getStringExtra(H.chapterName)!!
            val assignedDate = getStringExtra(H.assignedDate)!!
            val submissionDate = getStringExtra(H.submissionDate)!!
            val exerciseTitle = getStringExtra(H.exerciseTitle)!!
            val details = getStringExtra(H.details)!!
            val pageIndex = getStringExtra(H.pageIndex)!!
            val flag = getIntExtra(H.flag, Document.CREATED)
            val documentId = getStringExtra(H.documentId)!!
            val session = getStringExtra(H.session)!!
            val variant = getStringExtra(H.variant)!!
            val isYearSwitchChecked = getBooleanExtra(H.hasYearSwitchChecked, false)
            val year = getStringExtra(H.year)!!
            val collectionSize = getIntExtra(H.collectionSize, -1)

            val fragmentName = getStringExtra(H.fragmentName)!!

            val taskDataModel = TaskDataModel(chapterName = chapterName, assignedDate = assignedDate, submissionDate = submissionDate, flag =  flag,
                documentId = documentId, exerciseTitle = exerciseTitle, pageIndex = pageIndex, details = details, variant = variant,
                hasYearSwitchChecked = isYearSwitchChecked, year = year, session = session
            )

            return IntentDataModel(taskDataModel, fragmentName, collectionSize)
        }
    }

    fun setIntentData (intent: Intent, chapterName: String, assignedDate: String, collectionSize: Int, details: String, pageIndex: String,
                       submissionDate: String, documentId: String, flag: Int, hasYearSwitchChecked: Boolean, variant: String,
                       year: String, session: String, exerciseTitle: String, fragmentName: String
    ) {
        intent.apply {
            putExtra(H.chapterName, chapterName)
            putExtra(H.assignedDate, assignedDate)
            putExtra(H.hasYearSwitchChecked, hasYearSwitchChecked)
            putExtra(H.submissionDate, submissionDate)
            putExtra(H.collectionSize, collectionSize)
            putExtra(H.details, details)
            putExtra(H.pageIndex, pageIndex)
            putExtra(H.variant, variant)
            putExtra(H.year, year)
            putExtra(H.assignedDate, assignedDate)
            putExtra(H.documentId, documentId)
            Log.d("DocumentIdNotWorking", "Helper class $documentId")
            putExtra(H.flag, flag)
            putExtra(H.fragmentName, fragmentName)
            putExtra(H.session, session)
            putExtra(H.exerciseTitle, exerciseTitle)
        }
    }
}