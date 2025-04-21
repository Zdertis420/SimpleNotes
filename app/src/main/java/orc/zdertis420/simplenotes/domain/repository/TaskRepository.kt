package orc.zdertis420.simplenotes.domain.repository

import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.entity.Task

interface TaskRepository {
    fun saveTasks(tasks: List<TaskDto>)

    fun loadTasks(): List<TaskDto>
}