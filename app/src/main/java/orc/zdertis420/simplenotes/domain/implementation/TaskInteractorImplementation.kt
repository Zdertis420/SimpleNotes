package orc.zdertis420.simplenotes.domain.implementation

import android.util.Log
import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.domain.repository.TaskRepository

class TaskInteractorImplementation(val taskRepository: TaskRepository) : TaskInteractor {
    override fun saveTasks(tasks: List<TaskDto>) {
        Log.d("SAVE TASK", "interactor")

        taskRepository.saveTasks(tasks)
    }

    override fun loadTasks(): List<TaskDto> {
        return taskRepository.loadTasks()
    }

}