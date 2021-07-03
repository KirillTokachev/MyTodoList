package com.example.mytodolist.ui.main.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.mytodolist.ADD_TASK_RESULT_OK
import com.example.mytodolist.EDIT_TASK_RESULT_OK
import com.example.mytodolist.data.PreferencesManager
import com.example.mytodolist.data.SortOrder
import com.example.mytodolist.data.Task
import com.example.mytodolist.data.TaskDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class TaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    //...
    val searchQuery = state.getLiveData("searchQuery", "")

    val preferencesFlow = preferencesManager.preferencesFlow

    //...
    private val tasksEventChannel = Channel<TasksEvent>()
    //...
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    //...
    private val taskFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        taskDao.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }

    val tasks = taskFlow.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateEditTask(task))
    }

    fun onCheckBoxChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = isChecked))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        tasksEventChannel.send(TasksEvent.ShowDeleteTaskMessage(task))
    }

    fun onCancelDelete(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    fun onAddNewTask() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateAddTask)
    }

    fun onAddEditResult(result: Int) {
        when(result){
            ADD_TASK_RESULT_OK -> showTaskConfirnedMessage("Task added")
            EDIT_TASK_RESULT_OK -> showTaskConfirnedMessage("Task update")
        }
    }

    private fun showTaskConfirnedMessage(text: String) = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.ShowTaskConfirnedMessage(text))
    }

    fun onDeleteAllCompleted() = viewModelScope.launch {
        tasksEventChannel.send(TasksEvent.NavigateDeleteAllCompleted)
    }

    //...
    sealed class TasksEvent {
        object NavigateAddTask: TasksEvent()
        data class NavigateEditTask(val  task: Task) : TasksEvent()
        data class ShowDeleteTaskMessage(val task: Task) : TasksEvent()
        data class ShowTaskConfirnedMessage(val msg: String) : TasksEvent()
        object NavigateDeleteAllCompleted: TasksEvent()
    }
}

