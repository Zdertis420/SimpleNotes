package orc.zdertis420.simplenotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import orc.zdertis420.simplenotes.data.toDto
import orc.zdertis420.simplenotes.data.toTask
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.ui.state.TaskState

class TaskViewModel(
    private val taskInteractor: TaskInteractor
) : ViewModel() {

    private val _taskStateLiveData = MutableLiveData<TaskState>()
    val taskStateLiveData: LiveData<TaskState> get() = _taskStateLiveData

    fun saveTasks(activeTasks: List<Task>, completedTask: List<Task>) {
        taskInteractor.saveActiveTasks(activeTasks.map { it.toDto() })
        taskInteractor.saveCompletedTasks(completedTask.map { it.toDto() })
    }

    fun addTask(name: String, category: String, description: String) {
        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }.toMutableList()

        val lastTask = activeTasks.lastOrNull()

        val newTask = Task(
            name = name,
            category = category,
            description = description,
            id = lastTask?.id?.plus(1)?:0,
            completed = false,
            timestamp = System.currentTimeMillis()
        )

        activeTasks.add(newTask)

        taskInteractor.saveActiveTasks(activeTasks.map { it.toDto() })
    }

    fun updateTask(id: Int, name: String, category: String, description: String) {

    }

    fun updateCompleteness(id: Int, completed: Boolean) {
        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }.toMutableList()
        val completedTasks = taskInteractor.loadCompletedTasks().map { it.toTask() }.toMutableList()
        val task = if (completed) {
            activeTasks.find { it.id == id }
        } else {
            completedTasks.find { it.id == id }
        }!!

        val updated = Task(
            name = task.name,
            category = task.category   ,
            description = task.description,
            id = id,
            completed = completed,
            timestamp = task.timestamp
        )

        if (completed) {
            completedTasks.add(updated)
        } else {
            activeTasks.add(updated)
        }

        taskInteractor.saveActiveTasks(activeTasks.map { it.toDto() })
        taskInteractor.saveCompletedTasks(completedTasks.map { it.toDto() })
    }

    fun loadActiveTasks() {
        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }

        _taskStateLiveData.postValue(TaskState.Loaded.Active(activeTasks))
    }

    fun loadCompletedTasks() {
        val completedTasks = taskInteractor.loadCompletedTasks().map { it.toTask() }

        _taskStateLiveData.postValue(TaskState.Loaded.Active(completedTasks))
    }
}
