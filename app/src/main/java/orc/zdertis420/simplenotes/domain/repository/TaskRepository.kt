package orc.zdertis420.simplenotes.domain.repository

import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.entity.TaskType

interface TaskRepository {
    fun saveTasks(activeTasks: List<TaskDto>, completedTasks: List<TaskDto>)

    fun loadTasks(): Map<TaskType, List<TaskDto>>
}