package com.example.mytodolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.ui.main.viewmodel.fragments.MainFragment

class MainAdapter(private val task: List<String>, mainFragment: MainFragment):
    RecyclerView.Adapter<MainAdapter.MainFragmentViewHolder>() {

    class MainFragmentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var checkBoxDayTask: CheckBox? = null
        var titleTaskTextView: TextView? = null
        var taskTextTextView: TextView? = null

        init {
            checkBoxDayTask = itemView.findViewById(R.id.check_box_task)
            titleTaskTextView = itemView.findViewById(R.id.title_task)
            taskTextTextView = itemView.findViewById(R.id.task_text)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return MainAdapter.MainFragmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        /*holder.itemView.setOnClickListener {
            Log.d("SHOW", "${getItemId(position)}")
        }*/
    }

    override fun getItemCount(): Int {
        return task.size
    }

}