package orc.zdertis420.simplenotes.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.domain.entity.Task

class TaskViewHolder(
    itemView: View,
    private val onItemClickListener: ((position: Int) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    private val taskName: TextView = itemView.findViewById(R.id.task_name)
    private val taskCategory: TextView = itemView.findViewById(R.id.task_category)
    private val taskCompleted: MaterialCheckBox = itemView.findViewById(R.id.task_completed)

    fun bind(model: Task) {
        taskName.text = model.name
        taskCategory.text = model.category
        taskCompleted.isChecked = model.completed

        taskName.isSelected = true
    }

    init {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener?.invoke(position)
            }
        }
    }
}