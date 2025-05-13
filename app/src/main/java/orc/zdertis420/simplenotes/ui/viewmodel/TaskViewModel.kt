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

    fun saveActiveTasks(activeTasks: List<Task>) {
        taskInteractor.saveActiveTasks(activeTasks.map { it.toDto() })
    }

    fun saveCompletedTasks(completedTask: List<Task>) {
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

        _taskStateLiveData.postValue(TaskState.Saved)
    }

    fun updateTask(task: Task, name: String, category: String, description: String) {
        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }.toMutableList()
        val completedTasks = taskInteractor.loadCompletedTasks().map { it.toTask() }.toMutableList()

        Log.d("TASK", "Updating task: $task")

        val updated = Task(
            name = name,
            category = category,
            description = description,
            id = task.id,
            completed = task.completed,
            timestamp = task.timestamp
        )

        if (task.completed) {
            completedTasks.remove(task)
            completedTasks.add(updated)
        } else {
            activeTasks.remove(task)
            activeTasks.add(updated)
        }

        taskInteractor.saveActiveTasks(activeTasks.map { it.toDto() })
        taskInteractor.saveCompletedTasks(completedTasks.map { it.toDto() })
    }

    fun completeTask(task: Task) {
        Log.d("TASK", "Completing task: ${task.name}")

        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }.toMutableList()
        val completedTasks = taskInteractor.loadCompletedTasks().map { it.toTask() }.toMutableList()

        activeTasks.remove(task)

        completedTasks.add(Task(
            name = task.name,
            category = task.category,
            description = task.description,
            id = task.id,
            completed = true,
            timestamp = task.timestamp
        ))

        taskInteractor.saveActiveTasks(activeTasks.map { it.toDto() })
        taskInteractor.saveCompletedTasks(completedTasks.map { it.toDto() })
    }

    fun uncompleteTask(task: Task) {
        Log.d("TASK", "Uncompleting task: ${task.name}")

        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }.toMutableList()
        val completedTasks = taskInteractor.loadCompletedTasks().map { it.toTask() }.toMutableList()

        completedTasks.remove(task)

        activeTasks.add(Task(
            name = task.name,
            category = task.category,
            description = task.description,
            id = task.id,
            completed = false,
            timestamp = task.timestamp
        ))

        taskInteractor.saveActiveTasks(activeTasks.map { it.toDto() })
        taskInteractor.saveCompletedTasks(completedTasks.map { it.toDto() })
    }

    fun loadActiveTasks() {
        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }

        Log.d("TASK", "Loading active tasks: $activeTasks")

        _taskStateLiveData.postValue(TaskState.Loaded.Active(activeTasks))
    }

    fun loadCompletedTasks() {
        val completedTasks = taskInteractor.loadCompletedTasks().map { it.toTask() }

        Log.d("TASK", "Loading completed tasks: $completedTasks")

        _taskStateLiveData.postValue(TaskState.Loaded.Completed(completedTasks))
    }

    fun removeTask(task: Task) {
        Log.d("TASK", "Removal. VM stage")

        val activeTasks = taskInteractor.loadActiveTasks().map { it.toTask() }.toMutableList()
        val completedTasks = taskInteractor.loadCompletedTasks().map { it.toTask() }.toMutableList()

        try {
            activeTasks.remove(task)
        } catch (e: Exception) {
            Log.e("TASK", e.toString())
        }
    }
}
