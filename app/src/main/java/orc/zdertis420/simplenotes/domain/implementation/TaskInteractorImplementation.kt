package orc.zdertis420.simplenotes.domain.implementation

import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.entity.TaskType
import orc.zdertis420.simplenotes.domain.interactor.TaskInteractor
import orc.zdertis420.simplenotes.domain.repository.TaskRepository

class TaskInteractorImplementation(val taskRepository: TaskRepository) : TaskInteractor {
    override fun saveTasks(
        activeTasks: List<TaskDto>,
        completedTasks: List<TaskDto>
    ) {
        taskRepository.saveTasks(activeTasks, completedTasks)
    }

    override fun loadTasks(): Map<TaskType, List<TaskDto>> {
        return taskRepository.loadTasks()
    }
}