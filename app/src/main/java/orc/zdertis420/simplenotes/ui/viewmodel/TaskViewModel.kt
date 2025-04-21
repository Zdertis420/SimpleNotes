package orc.zdertis420.simplenotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.data.toDto
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.ui.state.TaskState

class TaskViewModel(
    private val taskInteractor: TaskInteractor
) : ViewModel() {

    private val _taskStateLiveData = MutableLiveData<TaskState>()
    val taskStateLiveData get() = _taskStateLiveData

    fun saveTask(name: String, category: String, description: String) {
        Log.d("SAVE TASK", "view model")

        if (name.isEmpty()) {
            _taskStateLiveData.postValue(TaskState.Error.SavingError)
            return
        }

        var tasks: MutableList<TaskDto> = taskInteractor.loadTasks().toMutableList()

        val lastTask = tasks.lastOrNull()
        
        val newTask = Task(
            name = name,
            category = category,
            description = description,
            id = lastTask?.id?.plus(1) ?: 0,
            completed = false,
            timestamp = System.currentTimeMillis()
        )

        tasks.add(newTask.toDto())

        taskInteractor.saveTasks(tasks)

        _taskStateLiveData.postValue(TaskState.Saved)
    }
}