package com.kg.taskapp.data.local.db

import androidx.room.*
import com.kg.taskapp.model.Task

@Dao
interface TaskDao {

    @Query("DELETE FROM Task WHERE id = :id")
    fun deleteById(id: Int)

    @Update
    fun update(task: Task)

    @Insert
    fun insert(task: Task)

    @Query("SELECT * FROM Task ORDER BY id DESC")
    fun getAll(): List<Task>

}