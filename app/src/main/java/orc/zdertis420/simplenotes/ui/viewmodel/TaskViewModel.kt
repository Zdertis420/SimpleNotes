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

    private val taskDtos = taskInteractor.loadTasks().toMutableList()

    fun saveTask(name: String, category: String, description: String) {
        Log.d("SAVE TASK", "view model")

        if (name.isEmpty()) {
            _taskStateLiveData.postValue(TaskState.Error.SavingError)
            return
        }

        val lastTask = taskDtos.lastOrNull()
        
        val newTask = Task(
            name = name,
            category = category,
            description = description,
            id = lastTask?.id?.plus(1) ?: 0,
            completed = false,
            timestamp = System.currentTimeMillis()
        )

        taskDtos.add(newTask.toDto())

        taskInteractor.saveTasks(taskDtos)

        _taskStateLiveData.postValue(TaskState.Saved)
    }

    fun loadTasks(taskType: TaskType) {
        val tasks = taskDtos.map { task ->
            task.toTask()
        }

        when (taskType) {
            TaskType.ALL -> _taskStateLiveData.postValue(TaskState.Loaded(tasks))
            TaskType.ACTIVE -> _taskStateLiveData.postValue(TaskState.Loaded(tasks.filter { it.completed == false }))
            TaskType.COMPLETED -> _taskStateLiveData.postValue(TaskState.Loaded(tasks.filter { it.completed == true }))
        }
    }
}