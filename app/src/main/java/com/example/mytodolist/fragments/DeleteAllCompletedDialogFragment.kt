package com.example.mytodolist.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mytodolist.ui.main.viewmodel.DeleteAllCompletedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllCompletedDialogFragment : DialogFragment() {

    private val viewModel: DeleteAllCompletedViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Подтвердите удаление")
            .setMessage("Вы действительно хотите удалить все выполненные задачи?")
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Да") { _, _ ->
                viewModel.onConfirmC()
            }
            .create()
}