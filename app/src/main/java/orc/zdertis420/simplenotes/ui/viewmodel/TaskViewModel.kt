package orc.zdertis420.simplenotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import orc.zdertis420.simplenotes.data.toDto
import orc.zdertis420.simplenotes.data.toTask
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.domain.entity.TaskType
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.ui.state.TaskState

class TaskViewModel(
    private val taskInteractor: TaskInteractor
) : ViewModel() {

    private val _taskStateLiveData = MutableLiveData<TaskState>()
    val taskStateLiveData get() = _taskStateLiveData

    fun saveTask(name: String, category: String, description: String) {
        Log.d("SAVE TASK", "view model")

        val taskDtos = taskInteractor.loadTasks()
        val activeTasks = taskDtos[TaskType.ACTIVE]?.map { it.toTask() }?.toMutableList() ?: mutableListOf()

        if (name.isEmpty()) {
            _taskStateLiveData.postValue(TaskState.Error.SavingError)
            return
        }

        val lastTask = activeTasks.lastOrNull()

//        Log.d("TASK", "Last task:\nName: ${lastTask?.name}; Id: ${lastTask?.id}")

        val newTask = Task(
            name = name,
            category = category,
            description = description,
            id = lastTask?.id?.plus(1) ?: 0,
            completed = false,
            timestamp = System.currentTimeMillis()
        )

//        Log.d("TASK", "New task:\nName: ${newTask.name}; Id: ${newTask.id}")

        activeTasks.add(newTask)

        taskInteractor.saveTasks(activeTasks.map { it.toDto() }.toList(), taskDtos[TaskType.COMPLETED]?:emptyList())

        _taskStateLiveData.postValue(TaskState.Saved)
    }

    fun updateTask(task: Task, name: String, category: String, description: String) {
        Log.d("SAVE TASK", "view model")

        val taskDtos = taskInteractor.loadTasks()
        val activeTasks = taskDtos[TaskType.ACTIVE]!!.map { it.toTask() }.toMutableList()
        val completedTasks = taskDtos[TaskType.COMPLETED]!!.map { it.toTask() }.toMutableList()

        val updatedTask = Task(
            name = name,
            category = category,
            description = description,
            id = task.id,
            completed = task.completed,
            timestamp = System.currentTimeMillis()
        )

        if (activeTasks.contains(task) == true) {
            activeTasks[task.id] = updatedTask
        } else {
            completedTasks[task.id] = updatedTask

        }

        taskInteractor.saveTasks(activeTasks.map { it.toDto() }.toList(), completedTasks.map { it.toDto() }.toList())

        _taskStateLiveData.postValue(TaskState.Saved)
    }

    fun updateCompleteness(task: Task, completed: Boolean) {
        val taskDtos = taskInteractor.loadTasks()
        val activeTasks = taskDtos[TaskType.ACTIVE]!!.map { it.toTask() }.toMutableList()
        val completedTasks = taskDtos[TaskType.COMPLETED]!!.map { it.toTask() }.toMutableList()

        val updatedTask = Task(
            name = task.name,
            category = task.category,
            description = task.description,
            id = task.id,
            completed = completed,
            timestamp = task.timestamp
        )

        if (activeTasks.contains(task) == true) {
            activeTasks.remove(task)
            completedTasks.add(updatedTask)
        } else {
            completedTasks.remove(task)
            activeTasks.add(task)
        }

        taskInteractor.saveTasks(activeTasks.map { it.toDto() }.toList(), completedTasks.map { it.toDto() }.toList())

        _taskStateLiveData.postValue(TaskState.Saved)
    }

    fun removeTask(task: Task) {
        val taskDtos = taskInteractor.loadTasks()
        val activeTasks = taskDtos[TaskType.ACTIVE]?.toMutableList() ?: mutableListOf()
        val completedTasks = taskDtos[TaskType.COMPLETED]?.toMutableList() ?: mutableListOf()

        if (activeTasks.contains(task.toDto())) {
            activeTasks.remove(task.toDto())
        } else {
            completedTasks.remove(task.toDto())
        }

        taskInteractor.saveTasks(activeTasks, completedTasks)
    }

    fun loadTasks(taskType: TaskType) {
        val taskDtos = taskInteractor.loadTasks()

        when(taskType) {
            TaskType.ACTIVE -> _taskStateLiveData.postValue(TaskState.Loaded(
                taskDtos[TaskType.ACTIVE]?.map { it.toTask() } ?: mutableListOf()
            ))
            TaskType.COMPLETED -> _taskStateLiveData.postValue(TaskState.Loaded(
                taskDtos[TaskType.COMPLETED]?.map { it.toTask() } ?: mutableListOf()
            ))
        }
    }
}