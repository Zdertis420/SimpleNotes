package orc.zdertis420.simplenotes.ui.state

import orc.zdertis420.simplenotes.domain.entity.Task

sealed class TaskState {
    object Initial : TaskState()

    object Saved : TaskState()

    sealed class Loaded : TaskState() {
        data class Active(val activeTasks: List<Task>) : Loaded()
        data class Completed(val completedTasks: List<Task>) : Loaded()
    }
}