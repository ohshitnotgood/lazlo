package io.github.praanto__samadder.cocoa.experimental

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import io.github.praanto__samadder.cocoa.boilerplate.room.TasksDatabase
import io.github.praanto__samadder.cocoa.model.H

class ExperimentalViewModel(application: Application): AndroidViewModel(application) {

    private val dao = TasksDatabase.getInstance(application.applicationContext).TasksDao()

    val items = Pager(PagingConfig(
        pageSize = 50,
        enablePlaceholders = true,
        maxSize = 200
    )) {
        Log.d(H.initTag, "Pager Flow initialised")
        dao.getTasksPagedById()
    }.flow
}