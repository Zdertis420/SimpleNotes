package orc.zdertis420.simplenotes.domain.repository

import orc.zdertis420.simplenotes.domain.entity.Task

interface TaskRepository {
    fun saveTasks(tasks: List<Task>)

    fun loadTasks(callback: (Result<List<Task>>) -> Unit)
}