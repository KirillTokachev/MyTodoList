package com.example.mytodolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.R
import com.example.mytodolist.adapters.DayTaskRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_view_task_a_day.*
import kotlinx.android.synthetic.main.fragment_view_task_a_day.view.*

class ViewTaskADayFragment : Fragment(), DayTaskRecyclerViewAdapter.OnItemClickListener {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_view_task_a_day, container, false)
        view.dayTaskRecyclerView.layoutManager = LinearLayoutManager(activity)
        view.dayTaskRecyclerView.adapter = DayTaskRecyclerViewAdapter(task = mutableListOf<String>("Test"), this)
        return view
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "Item $position clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = arguments?.getString("DayArg")
        textView.text = text

    }

}