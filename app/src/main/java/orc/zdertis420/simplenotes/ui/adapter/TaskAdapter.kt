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
    private val listener: TaskListener,
    private val isListenerEnabled: () -> Boolean
    ) :
    ListAdapter<Task, TaskViewHolder>(TaskDiffCallback()) {

    private var listenerEnabled = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val views = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TaskViewHolder(
            views,
            ::isListenersEnabled,
            listener
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    override fun submitList(list: List<Task?>?) {
        super.submitList(list)
        Log.d("Task list", "New tasks submitted: $list")
    }

    fun setListenersEnabled(enabled: Boolean) {
        listenerEnabled = enabled
    }

    private fun isListenersEnabled(): Boolean = listenerEnabled
}