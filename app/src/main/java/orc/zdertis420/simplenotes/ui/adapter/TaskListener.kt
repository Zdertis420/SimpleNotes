package orc.zdertis420.simplenotes.ui.adapter

import android.view.View
import orc.zdertis420.simplenotes.domain.entity.Task

interface TaskListener {
    fun onTask(task: Task)

    fun onCheckbox(task: Task, isChecked: Boolean)

    fun onOverflowMenu(task: Task, anchor: View)
}