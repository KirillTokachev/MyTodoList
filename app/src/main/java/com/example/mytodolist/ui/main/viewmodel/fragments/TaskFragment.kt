package com.example.mytodolist.ui.main.viewmodel.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.R
import com.example.mytodolist.adapters.TasksAdapter
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.example.mytodolist.ui.main.viewmodel.TaskViewModel
import com.example.mytodolist.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_task.*

@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_task){

    private val viewModel: TaskViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dataCalendarText = date_calendar
        val text = arguments?.getString("DayArg")
        dataCalendarText.text = text

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

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_sort_by_name -> {

                true
            }
            R.id.action_sort_by_date_created -> {

                true
            }
            R.id.action_hide_completed_tasks -> {
               true
            }
            R.id.action_delete_all_complete_tasks -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}