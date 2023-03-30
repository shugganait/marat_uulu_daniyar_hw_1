package com.kg.taskapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kg.taskapp.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}