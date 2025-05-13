package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import orc.zdertis420.simplenotes.databinding.FragmentTaskActiveBinding
import orc.zdertis420.simplenotes.ui.adapter.TaskAdapter
import orc.zdertis420.simplenotes.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.data.toDto
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.ui.state.TaskState

class ActiveFragment : Fragment() {

    private var _views: FragmentTaskActiveBinding? = null
    private val views get() = _views!!

    private val viewModel by viewModel<TaskViewModel>()

    private var tasks: List<Task> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentTaskActiveBinding.inflate(inflater, container, false)

        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views.tasks.layoutManager = LinearLayoutManager(context)
        views.tasks.adapter = TaskAdapter(
            tasks,
            onOverflowMenu = { position, anchor ->
                showPopupMenu(tasks[position], anchor)
            },
            onCheckbox = { position, isChecked ->
                val task = tasks[position]

                Log.d("TASK", "Task checked: ${task.name}")

                viewModel.completeTask(task)
                viewModel.loadActiveTasks()
            },
            onItem = { position ->
                val task = tasks[position]
                Log.d("TASK", "Task clicked: ${task.name}")

                val args = bundleOf("task" to task.toDto())
                findNavController().navigate(R.id.action_homeFragment_to_taskFragment, args)
            }
        )

        viewModel.taskStateLiveData.observe(viewLifecycleOwner) { state ->
            react(state)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadActiveTasks()
    }

    private fun react(state: TaskState) {
        when (state) {
            is TaskState.Loaded.Active -> updateTasks(state.activeTasks)
            else -> {}
        }
    }

    private fun updateTasks(tasks: List<Task>) {
        this.tasks = tasks

        (views.tasks.adapter as TaskAdapter).updateTasks(tasks)
    }

    private fun showPopupMenu(task: Task, anchor: View) {
        val popupMenu = PopupMenu(requireContext(), anchor)
        popupMenu.menuInflater.inflate(R.menu.task_action_popup, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.task_action_edit -> {
                    Log.d("TASK", "Editing task: ${task.name}")

                    val args = bundleOf("task" to task.toDto())
                    findNavController().navigate(R.id.action_homeFragment_to_editTaskFragment, args)

                    true
                }

                R.id.task_action_delete -> {
                    Log.d("TASK", "Delete task: ${task.name}")

                    viewModel.removeTask(task)
                    viewModel.loadActiveTasks()

                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onPause() {
        super.onPause()

        viewModel.saveActiveTasks(tasks)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _views = null
    }
}