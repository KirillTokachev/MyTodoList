package com.example.mytodolist.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.mytodolist.data.TaskDao
import com.example.mytodolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//...
class DeleteAllCompletedViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    //...
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmC() = applicationScope.launch {
        taskDao.deleteCompletedTasks()
    }
}