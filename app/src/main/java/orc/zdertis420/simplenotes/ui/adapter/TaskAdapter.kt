package orc.zdertis420.simplenotes.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.R

class TaskAdapter(
    private var tasks: List<Task>,
    private val onOverflowMenu: ((Int, View) -> Unit)?,
    private val onCheckbox: ((Int, Boolean) -> Unit)?,
    private val onItem: ((Int) -> Unit)?
) : RecyclerView.Adapter<TaskViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.task_item,
                    parent,
                    false
                ),
            onOverflowMenu,
            onCheckbox,
            onItem
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: List<Task>) {
        this.tasks = newTasks
        notifyDataSetChanged()
    }
}