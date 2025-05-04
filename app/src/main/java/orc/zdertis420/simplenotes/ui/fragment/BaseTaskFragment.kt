package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.data.toDto
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

    private var tasks = listOf<Task>()

    private val activityNavController = requireActivity().findNavController(R.id.main_fragment_container)

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

        tasksRecyclerView.adapter = TaskAdapter(
            tasks,
            onOverflowMenu = { position, anchor ->
                Log.d("TASK", "Overflow menu clicked for task: ${tasks[position].name}")

                showPopupMenu(tasks[position], anchor)

                val args = bundleOf("task" to tasks[position].toDto())
                activityNavController.navigate(R.id.action_homeFragment_to_editTaskFragment, args)
            },
            onCheckbox = { position, isChecked ->
                Log.d("TASK", "Task ${tasks[position].name} completed: $isChecked")
            },
            onItem = { position ->
                Log.d("TASK", "Task clicked: ${tasks[position].name}")
            }
        )

        viewModel.taskStateLiveData.observe(viewLifecycleOwner) { state ->
            execute(state)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadTasks(getTasksType())
    }

    protected fun execute(state: TaskState) {
        when (state) {
            is TaskState.Loaded -> updateTasks(state.tasks)
            else -> {}
        }
    }

    private fun updateTasks(tasks: List<Task>) {
        this.tasks = tasks
        (tasksRecyclerView.adapter as TaskAdapter).updateTasks(tasks)
    }

    private fun showPopupMenu(task: Task, anchor: View) {
        val popupMenu = PopupMenu(requireContext(), anchor)
        popupMenu.menuInflater.inflate(R.menu.task_action_popup, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.task_action_edit -> {
                    Log.d("TASK", "Editing task: ${task.name}")



                    true
                }

                R.id.task_action_delete -> {
                    Log.d("TASK", "Delete task: ${task.name}")
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }
}