package orc.zdertis420.simplenotes.domain.implementation

import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.domain.repository.TaskRepository

class TaskInteractorImplementation(val taskRepository: TaskRepository) : TaskInteractor {
    override fun saveActiveTasks(activeTasks: List<TaskDto>) {
        taskRepository.saveActiveTasks(activeTasks)
    }

    override fun saveCompletedTasks(completedTasks: List<TaskDto>) {
        taskRepository.saveCompletedTasks(completedTasks)
    }

    override fun loadActiveTasks(): List<TaskDto> {
        return taskRepository.loadActiveTasks()
    }

    override fun loadCompletedTasks(): List<TaskDto> {
        return taskRepository.loadCompletedTasks()
    }
}