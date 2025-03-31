package orc.zdertis420.simplenotes.domain.interactor

import orc.zdertis420.simplenotes.domain.entity.Task

interface TaskInteractor {
    fun saveTasks(tasks: List<Task>)

    fun loadTasks(callback: (Result<List<Task>>) -> Unit)
}