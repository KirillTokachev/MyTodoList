package com.example.mytodolist.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.adapters.TasksAdapter
import com.example.mytodolist.data.SortOrder
import com.example.mytodolist.data.Task
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.example.mytodolist.ui.main.viewmodel.TaskViewModel
import com.example.mytodolist.util.exhaustive
import com.example.mytodolist.util.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch




@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_task), TasksAdapter.OnItemClickListener{

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var searchView: SearchView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dataCalendarText = date_calendar
        val text = arguments?.getString("DayArg")
        dataCalendarText.text = text

        val binding = FragmentTaskBinding.bind(view)

        val taskAdapter = TasksAdapter(this)

        binding.apply {
            recyclerViewTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val task = taskAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onTaskSwiped(task)
                }
            }).attachToRecyclerView(recyclerViewTasks)

            //...
            fbAddTask.setOnClickListener {
                viewModel.onAddNewTask()
            }
        }



        //...
        setFragmentResultListener("add_edit_request") { _, bundle ->
            var result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

        //...
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect { event ->
                when (event) {
                    is TaskViewModel.TasksEvent.ShowDeleteTaskMessage -> {
                        Snackbar.make(requireView(), "???????????? ??????????????", Snackbar.LENGTH_LONG)
                            .setAction("????????????") {
                                viewModel.onCancelDelete(event.task)
                            }.show()
                    }
                    is TaskViewModel.TasksEvent.NavigateAddTask -> {
                        val action = TaskFragmentDirections.actionViewTaskToTaskInfo(null,"???????????? ??????????????????")
                        findNavController().navigate(action)
                    }
                    is TaskViewModel.TasksEvent.NavigateEditTask -> {
                        val action = TaskFragmentDirections.actionViewTaskToTaskInfo(event.task, "???????????? ??????????????????????????????")
                        findNavController().navigate(action)
                    }
                    is TaskViewModel.TasksEvent.ShowTaskConfirnedMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    TaskViewModel.TasksEvent.NavigateDeleteAllCompleted -> {
                        val action = TaskFragmentDirections.actionGlobalDeleteAllCompletedDialogFragment()
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }

        setHasOptionsMenu(true)

    }

    override fun onItemClickListener(task: Task) {
        viewModel.onTaskSelected(task)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onCheckBoxChanged(task, isChecked)
    }

    //...
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery != null && pendingQuery.isEmpty()) {
            //...
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false);

        }

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }

        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_hide_completed_tasks).isChecked =
                viewModel.preferencesFlow.first().hideCompleted
        }

    }

    //...
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_sort_by_name -> {
                //viewModel.sortOrder.value = SortOrder.BY_NAME
                viewModel.onSortOrderSelected(SortOrder.BY_NAME)
                true
            }
            R.id.action_sort_by_date_created -> {
                //viewModel.sortOrder.value = SortOrder.BY_DATE
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            R.id.action_hide_completed_tasks -> {
                item.isChecked = !item.isChecked
                //viewModel.hideCompleted.value = item.isChecked
                viewModel.onHideCompletedClick(item.isChecked)
               true
            }
            R.id.action_delete_all_complete_tasks -> {
                viewModel.onDeleteAllCompleted()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }

}