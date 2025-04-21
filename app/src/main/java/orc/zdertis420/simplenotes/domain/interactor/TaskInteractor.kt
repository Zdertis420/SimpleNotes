package orc.zdertis420.simplenotes.domain.interactor

import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.entity.Task

interface TaskInteractor {
    fun saveTasks(tasks: List<TaskDto>)

    fun loadTasks(): List<TaskDto>
}