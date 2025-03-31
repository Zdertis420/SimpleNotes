package orc.zdertis420.simplenotes.domain.implementation

import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.domain.repository.TaskRepository

class TaskInteractorImplementation(val taskRepository: TaskRepository) : TaskInteractor {
    override fun saveTasks(tasks: List<Task>) {
        taskRepository.saveTasks(tasks)
    }

    override fun loadTasks(callback: (Result<List<Task>>) -> Unit) {
        taskRepository.loadTasks(callback)
    }

}