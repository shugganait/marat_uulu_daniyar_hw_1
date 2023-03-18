package com.kg.taskapp.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kg.taskapp.databinding.ItemTaskBinding
import com.kg.taskapp.model.Task

class TaskAdapter: Adapter<TaskAdapter.TaskViewHolder>() {

    private val data: ArrayList<Task> = arrayListOf()

    fun addTask(task: Task){
        data.add(0 ,task)
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }


    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                tvTitle.text = task.title
                tvDesc.text = task.desc
            }
        }

    }
}