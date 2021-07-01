    package com.example.mytodolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.R
import com.example.mytodolist.adapters.DayTaskRecyclerViewAdapter
import com.example.mytodolist.adapters.MainFragmentRecyclerViewAdapter
import com.example.mytodolist.ui.main.viewmodel.MainActivity
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*


    class MainFragment : Fragment(),DayTaskRecyclerViewAdapter.OnItemClickListener {

        var myList:List<String> = mutableListOf<String>("Text","Text","Text","Text","Text","Text")





        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {



            val view = inflater.inflate(R.layout.main_fragment, container, false)
            view.mainRecyclerView.layoutManager = LinearLayoutManager(activity)
            view.mainRecyclerView.adapter = MainFragmentRecyclerViewAdapter(myList, this)
            return view

           /* return inflater.inflate(R.layout.main_fragment, container, false)*/


        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            val bundle = Bundle()

            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

                var data: String = "$dayOfMonth.${month + 1}.$year"
                bundle.putString("DayArg", data)
                (activity as MainActivity).navController.navigate(R.id.viewTaskFragment, bundle)


            }

        }

        override fun onItemClick(position: Int) {
            TODO("Not yet implemented")
        }


    }




