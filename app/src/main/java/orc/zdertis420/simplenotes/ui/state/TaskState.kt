package orc.zdertis420.simplenotes.ui.state

import orc.zdertis420.simplenotes.domain.entity.Task

sealed class TaskState {
    object Saved : TaskState()
    data class Loaded(val tasks: List<Task>) : TaskState()
    sealed class Error : TaskState() {
        object SavingError : Error()
        data class LoadingError(val msg: String) : Error()
    }
}