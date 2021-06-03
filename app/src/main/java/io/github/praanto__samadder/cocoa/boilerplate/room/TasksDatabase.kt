package io.github.praanto__samadder.cocoa.boilerplate.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TasksEntity::class])
abstract class TasksDatabase : RoomDatabase() {

    abstract fun TasksDao() : TasksDao

    companion object {
        @Volatile
        private var INSTANCE : TasksDatabase? = null

        fun getInstance(context: Context): TasksDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    TasksDatabase::class.java, "CheeseDatabase")
                    .fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}