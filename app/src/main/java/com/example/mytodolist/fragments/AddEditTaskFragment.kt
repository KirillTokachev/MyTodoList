package com.example.mytodolist.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.AddEditTaskFragmentBinding
import com.example.mytodolist.ui.main.viewmodel.AddEditTaskViewModel
import com.example.mytodolist.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.add_edit_task_fragment) {

    //...
    private val viewModel: AddEditTaskViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = AddEditTaskFragmentBinding.bind(view)

        binding.apply {
            editTextTaskName.setText(viewModel.taskName)
            editTextTaskInfo.setText(viewModel.taskInfo)
            checkboxImportant.isChecked = viewModel.taskImportance
            //...
            checkboxImportant.jumpDrawablesToCurrentState()
            dateCreatedTextView.isVisible = viewModel.task != null
            dateCreatedTextView.text = "Создано: ${viewModel.task?.createdDateFormatted}"


            editTextTaskName.addTextChangedListener {
                viewModel.taskName = it.toString()
            }
            
            editTextTaskInfo.addTextChangedListener {
                viewModel.taskInfo = it.toString()
            }
            
            checkboxImportant.setOnCheckedChangeListener { _, isChecked ->
                viewModel.taskImportance = isChecked
            }

            fbSaveTask.setOnClickListener {
                viewModel.onSave()
            }

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditTaskEvent.collect { event ->
                when(event){
                    is AddEditTaskViewModel.AddEditTaskEvent.InvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditTaskViewModel.AddEditTaskEvent.BackWithResult -> {
                        binding.editTextTaskName.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}