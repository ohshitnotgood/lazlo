package io.github.praanto__samadder.cocoa.model

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class LogMan {

    fun debug(t: AppCompatActivity, msg: String) {
        val currentSimpleClassname = t.javaClass.kotlin
        val tag = currentSimpleClassname.toString()
        Log.d(tag, msg)
    }

    fun debug(subTag: String, msg: String) {

    }

    fun warn() {

    }

    fun error() {

    }
}