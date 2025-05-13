package orc.zdertis420.simplenotes.ui.adapter

import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.ui.viewmodel.TaskViewModel

interface TaskListener {
    fun onTaskClicked(task: Task)

    fun onCheckboxClicked(task: Task, isChecked: Boolean)

    fun onOverflowMenuClicked(task: Task, anchor: TaskViewModel)
}