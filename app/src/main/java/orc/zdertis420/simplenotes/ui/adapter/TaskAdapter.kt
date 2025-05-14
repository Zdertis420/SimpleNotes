package orc.zdertis420.simplenotes.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.R
import kotlin.invoke

class TaskAdapter(
    private val onOverflowMenu: ((Task, View) -> Unit)?,
    private val onCheckbox: ((Task, Boolean) -> Unit)?,
    private val onItem: ((Task) -> Unit)?
) : ListAdapter<Task, TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false),
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)

        holder.views.overflowMenu.setOnClickListener {
            val clickedTask = getItem(holder.bindingAdapterPosition)
            onOverflowMenu?.invoke(clickedTask, it)
        }

        holder.itemView.setOnClickListener {
            val clickedTask = getItem(holder.bindingAdapterPosition)
            onItem?.invoke(clickedTask)
        }

        holder.views.taskCompleted.setOnCheckedChangeListener(null)

        holder.views.taskCompleted.isChecked = task.completed

        holder.views.taskCompleted.setOnCheckedChangeListener { _, isChecked ->
            val taskToUpdate = getItem(holder.bindingAdapterPosition)
            onCheckbox?.invoke(taskToUpdate, isChecked)
        }
    }

    override fun submitList(list: List<Task?>?) {
        super.submitList(list)
        Log.d("Task list", "New tasks submitted: $list")
    }
}