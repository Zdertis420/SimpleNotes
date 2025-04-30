package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import orc.zdertis420.simplenotes.databinding.FragmentTaskBaseBinding
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.domain.entity.TaskType
import orc.zdertis420.simplenotes.ui.adapter.TaskAdapter
import orc.zdertis420.simplenotes.ui.state.TaskState
import orc.zdertis420.simplenotes.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseTaskFragment : Fragment() {

    private var _views: FragmentTaskBaseBinding? = null
    protected val views get() = _views!!

    protected val viewModel by viewModel<TaskViewModel>()

    protected lateinit var tasksRecyclerView: RecyclerView

    private lateinit var tasks: List<Task>

    abstract fun getTasksType(): TaskType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentTaskBaseBinding.inflate(inflater, container, false)

        tasksRecyclerView = RecyclerView(requireContext())
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        views.root.addView(tasksRecyclerView)

        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.taskStateLiveData.observe(viewLifecycleOwner) { state ->
            execute(state)
        }

        viewModel.loadTasks(getTasksType())


    }

    protected fun execute(state: TaskState) {
        when (state) {
            is TaskState.Loaded -> loadTasks(state.tasks)
            else -> {}
        }
    }

    private fun loadTasks(filteredTasks: List<Task>) {
        this.tasks = filteredTasks

//        Log.d("TASK", "Tasks in fragment:\nTasks names: ${filteredTasks.map { it.name }}")

        tasksRecyclerView.adapter = TaskAdapter(tasks)

        (tasksRecyclerView.adapter as TaskAdapter).setOnItemClickListener { position ->
            Log.d("TASK", "Clicked on task: ${tasks[position].name}")
        }
    }
}