package io.github.praanto__samadder.cocoa.experimental

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.boilerplate.room.TasksDatabase
import io.github.praanto__samadder.cocoa.boilerplate.room.TasksEntity
import io.github.praanto__samadder.cocoa.model.H
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Experimental activity where views and layouts are tested.
 * Basically a test class. But I'm too lazy to do an AndroidTest. So fuck it!
 */
class Experimental: AppCompatActivity() {

    private lateinit var adapter: ExperimentalListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val viewModel = ViewModelProvider(this)[ExperimentalViewModel::class.java]
//        prePopulateDatabase()

        adapter = ExperimentalListAdapter()
        pagerRecyclerView.adapter = adapter
        submitDataToListAdapter(viewModel)

        Log.d(H.initTag, "Adapter set inside Experimental Class")


    }

    private fun submitDataToListAdapter(viewModel: ExperimentalViewModel) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(H.initTag, "Coroutine scope launch inside Experimental class")
            viewModel.items.collectLatest {
                adapter.submitData(it)
                Log.d(H.initTag, "Send data to adapter class")
            }
        }
    }

    private fun prePopulateDatabase() {
        val dao = Room.databaseBuilder(application, TasksDatabase::class.java, "tasksDatabase")
            .build()
            .TasksDao()
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..50) {
                dao.insertTask(TasksEntity(id = 0, name = "Hello $i"))
            }
            val d = dao.getAllTasks()
            withContext(Dispatchers.Main) {
                Toast.makeText(baseContext, d.size.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}