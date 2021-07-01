    package com.example.mytodolist.ui.main.viewmodel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.R
import com.example.mytodolist.adapters.TasksAdapter
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.example.mytodolist.ui.main.viewmodel.MainActivity
import com.example.mytodolist.ui.main.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*


    @AndroidEntryPoint
    class MainFragment : Fragment() {

        private val viewModel: TaskViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            return inflater.inflate(R.layout.main_fragment, container, false)

        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            val bundle = Bundle()

            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

                var data: String = "$dayOfMonth.${month + 1}.$year"
                bundle.putString("DayArg", data)
                (activity as MainActivity).navController.navigate(R.id.viewTask, bundle)


            }


            val binding = FragmentTaskBinding.bind(view)

            val taskAdapter = TasksAdapter()

            binding.apply {
                recyclerViewTasks.apply {
                    adapter = taskAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                }
            }

            viewModel.tasks.observe(viewLifecycleOwner) {
                taskAdapter.submitList(it)
            }


        }

    }



