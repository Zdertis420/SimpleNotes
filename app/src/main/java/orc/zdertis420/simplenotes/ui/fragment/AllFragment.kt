package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import orc.zdertis420.simplenotes.domain.entity.TaskType

class AllFragment : BaseTaskFragment() {
    override fun getTasksType(): TaskType {
        return TaskType.ALL
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}