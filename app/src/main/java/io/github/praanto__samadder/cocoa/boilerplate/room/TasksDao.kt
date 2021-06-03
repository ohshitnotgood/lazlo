package io.github.praanto__samadder.cocoa.boilerplate.room

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface TasksDao {

    @Query("SELECT * FROM TASKS order by id")
    suspend fun getAllTasks() : List<TasksEntity>

    @Query("SELECT * FROM TASKS ORDER BY id DESC")
    fun getTasksPagedById() : PagingSource<Int, TasksEntity>

    @Update suspend fun updateTask(tasksEntity: TasksEntity) : Int

    @Insert suspend fun insertTask(tasksEntity: TasksEntity) : Long

    @Delete suspend fun deleteTask(tasksEntity: TasksEntity) : Int

}