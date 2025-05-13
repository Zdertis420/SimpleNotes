package orc.zdertis420.simplenotes.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import orc.zdertis420.simplenotes.domain.entity.Task

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return (oldItem.name == newItem.name &&
                oldItem.category == newItem.category &&
                oldItem.description == newItem.description &&
                oldItem.completed == newItem.completed &&
                oldItem.timestamp == newItem.timestamp)
    }
}