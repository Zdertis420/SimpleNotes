package orc.zdertis420.simplenotes.domain.interactor

import orc.zdertis420.simplenotes.data.dto.TaskDto

interface TaskInteractor {
    fun saveActiveTasks(activeTasks: List<TaskDto>)

    fun saveCompletedTasks(completedTasks: List<TaskDto>)

    fun loadActiveTasks(): List<TaskDto>

    fun loadCompletedTasks(): List<TaskDto>
}