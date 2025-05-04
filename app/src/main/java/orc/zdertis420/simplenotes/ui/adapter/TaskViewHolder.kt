package orc.zdertis420.simplenotes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import orc.zdertis420.simplenotes.databinding.TaskItemBinding
import orc.zdertis420.simplenotes.domain.entity.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskViewHolder(
    itemView: View,
    private val onOverflowMenu: ((Int, View) -> Unit)?,
    private val onCheckbox: ((Int, Boolean) -> Unit)?,
    private val onItem: ((Int) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    private val views = TaskItemBinding.bind(itemView)

    fun bind(model: Task) = with(views) {
        taskName.text = model.name
        taskCategory.text = model.category
        taskCompleted.isChecked = model.completed
        creationDate.text =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(model.timestamp)

        taskName.isSelected = true
        taskCategory.isSelected = true
    }

    init {
        with(views) {
            root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItem?.invoke(position)
                }
            }
            taskCompleted.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCheckbox?.invoke(position, isChecked)
                }
            }
            overflowMenu.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onOverflowMenu?.invoke(position, overflowMenu)
                }
            }
        }
    }
}