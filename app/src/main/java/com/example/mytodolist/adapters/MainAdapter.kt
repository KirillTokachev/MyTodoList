package com.example.mytodolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.data.Task
import com.example.mytodolist.databinding.ItemTaskBinding

class MainAdapter : ListAdapter<Task, MainAdapter.MainViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {

        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return MainViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val currentItem = getItem(position)
        holder.bind(currentItem)

    }

    class MainViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(task: Task){
            binding.apply {
                checkBoxTask.visibility = View.GONE
                nameTask.text = task.name
                nameTask.paint.isStrikeThruText = task.completed

                taskText.text = task.info
                taskText.paint.isStrikeThruText = task.completed

                priorityLabel.isVisible = task.important
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    }

}


