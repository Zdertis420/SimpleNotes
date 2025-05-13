package orc.zdertis420.simplenotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.R

class TaskAdapter(
    private var tasks: List<Task>,
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
        val task = tasks[position]
        holder.bind(task)

        holder.itemView.setOnClickListener {
            onItem?.invoke(task)
        }

        holder.views.overflowMenu.setOnClickListener {
            onOverflowMenu?.invoke(task, it)
        }

        holder.views.taskCompleted.setOnCheckedChangeListener { _, isChecked ->
            onCheckbox?.invoke(task, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}