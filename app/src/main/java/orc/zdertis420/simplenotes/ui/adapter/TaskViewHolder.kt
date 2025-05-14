package orc.zdertis420.simplenotes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import orc.zdertis420.simplenotes.databinding.TaskItemBinding
import orc.zdertis420.simplenotes.domain.entity.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskViewHolder(
    itemView: View,
    private val isListenerEnabled: () -> Boolean,
    private val listener: TaskListener
) : RecyclerView.ViewHolder(itemView) {

    val views = TaskItemBinding.bind(itemView)

    fun bind(model: Task) = with(views) {
        taskCompleted.setOnCheckedChangeListener(null)
        root.setOnClickListener(null)
        overflowMenu.setOnClickListener(null)

        taskName.text = model.name
        taskCategory.text = model.category
        taskCompleted.isChecked = model.completed
        creationDate.text =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(model.timestamp)

        taskName.isSelected = true
        taskCategory.isSelected = true

        taskCompleted.setOnCheckedChangeListener { _, isChecked ->
            if (isListenerEnabled()) {
                listener.onCheckbox(model, isChecked)
            }
        }

        root.setOnClickListener {
            if (isListenerEnabled()) {
                listener.onTask(model)
            }
        }

        overflowMenu.setOnClickListener {
            if (isListenerEnabled()) {
                listener.onOverflowMenu(model, it)
            }
        }
    }
}