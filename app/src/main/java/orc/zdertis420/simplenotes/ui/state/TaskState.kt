package orc.zdertis420.simplenotes.ui.state

import orc.zdertis420.simplenotes.domain.entity.Task

sealed class TaskState {
    object Saved : TaskState()

    sealed class Loaded : TaskState() {
        data class Active(val activeTasks: List<Task>) : Loaded()
        data class Completed(val completedTasks: List<Task>) : Loaded()
    }

    sealed class Error : TaskState() {
        object SavingError : Error()
        data class LoadingError(val msg: String) : Error()
    }
}