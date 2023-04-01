package com.kg.taskapp.ui.home.adapter

import android.R
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kg.taskapp.databinding.ItemTaskBinding
import com.kg.taskapp.model.Task
import kotlin.properties.Delegates


class TaskAdapter(
    var onItemClick: ((Task) -> Unit)? = null, var onItemLongClick: ((Task) -> Unit)? = null
) : Adapter<TaskAdapter.TaskViewHolder>() {

    private val data: ArrayList<Task> = arrayListOf()

    companion object {
        var getAdapterPosition by Delegates.notNull<Int>()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTasks(task: List<Task>) {
        data.clear()
        data.addAll(task)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun delete(todelete: Int) {
        data.removeAt(todelete)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
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
                setBlackAndWhiteBG()
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(data[adapterPosition])
            }
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(data[adapterPosition])
                getAdapterPosition = adapterPosition
                return@setOnLongClickListener true
            }
        }
        private fun setBlackAndWhiteBG() {
            binding.apply {
                if ((adapterPosition % 2 == 0)) {
                    tvTitle.setTextColor(Color.parseColor("#FFFFFF"))
                    tvDesc.setTextColor(Color.parseColor("#FFFFFF"))
                    itemTask.setBackgroundColor(Color.parseColor("#000000"))
                } else {
                    tvTitle.setTextColor(Color.parseColor("#000000"))
                    tvDesc.setTextColor(Color.parseColor("#000000"))
                    itemTask.setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
            }
        }
    }
}