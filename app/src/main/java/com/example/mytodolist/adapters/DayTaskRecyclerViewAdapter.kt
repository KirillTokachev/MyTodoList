package com.example.mytodolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.fragments.ViewTaskADayFragment


class DayTaskRecyclerViewAdapter(private val task: List<String>,
                                 private val listener: ViewTaskADayFragment
):RecyclerView.Adapter<DayTaskRecyclerViewAdapter.DayTaskViewHolder>() {

    inner class DayTaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

       /* var check_box_task: CheckBox? = null
        var title_task: TextView? = null
        var task_text: TextView? = null

        init {
            check_box_task = itemView.findViewById(R.id.check_box_task)
            title_task = itemView.findViewById(R.id.title_task)
            task_text = itemView.findViewById(R.id.task_text)
        }*/

        val checkBoxDayTask:CheckBox = itemView.findViewById(R.id.check_box_task)
        val titleTaskTextView:TextView = itemView.findViewById(R.id.title_task)
        val taskTextTextView:TextView = itemView.findViewById(R.id.task_text)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        /*override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }*/
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayTaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return DayTaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DayTaskViewHolder, position: Int) {

        val currentItem = task[position]

        holder.titleTaskTextView.text = currentItem.toString()
        holder.taskTextTextView.text = currentItem.toString()
        
    }

    override fun getItemCount(): Int {
        return task.size
    }

}