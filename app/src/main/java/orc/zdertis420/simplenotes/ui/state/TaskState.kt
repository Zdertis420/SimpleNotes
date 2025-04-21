package orc.zdertis420.simplenotes.ui.state

import orc.zdertis420.simplenotes.data.dto.TaskDto

sealed class TaskState {
    object Saved : TaskState()
    data class Loaded(val tasks: List<TaskDto>) : TaskState()
    sealed class Error : TaskState() {
        object SavingError : Error()
        data class LoadingError(val msg: String) : Error()
    }
}