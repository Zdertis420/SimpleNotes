package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import orc.zdertis420.simplenotes.databinding.FragmentTaskActiveBinding
import orc.zdertis420.simplenotes.ui.adapter.TaskAdapter
import orc.zdertis420.simplenotes.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.data.toDto
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.ui.adapter.TaskListener
import orc.zdertis420.simplenotes.ui.state.TaskState

class ActiveFragment : Fragment(), TaskListener {

    private var _views: FragmentTaskActiveBinding? = null
    private val views get() = _views!!

    private val viewModel by viewModel<TaskViewModel>()

    private var enableListenersJob: Job? = null
    private val DEBOUNCE_DELAY = 250L

    private lateinit var taskAdapter: TaskAdapter

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

        taskAdapter = TaskAdapter(this)
        setupRecycler()

        viewModel.taskStateLiveData.observe(viewLifecycleOwner) { state ->
            react(state)
        }
    }

    override fun onResume() {
        super.onResume()

        taskAdapter.setListenersEnabled(true)
        viewModel.loadActiveTasks()
    }

    private fun setupRecycler() {
        views.tasks.layoutManager = LinearLayoutManager(context)
        views.tasks.adapter = taskAdapter

    }

    private fun react(state: TaskState) {
        when (state) {
            is TaskState.Loaded.Active -> {
                Log.d("TASK", "Loaded tasks: ${state.activeTasks}")

                taskAdapter.setListenersEnabled(false)
                taskAdapter.submitList(state.activeTasks.sortedBy { it.id }) {
                    enableListenersJob?.cancel()
                    enableListenersJob = lifecycleScope.launch {
                        delay(DEBOUNCE_DELAY)
                        taskAdapter.setListenersEnabled(true)
                    }
                }
            }
            else -> {}
        }
    }

    override fun onCheckbox(task: Task, isChecked: Boolean) {
        Log.d("TASK", "Task checked: ${task.name}")

        viewModel.completeTask(task)
        viewModel.loadActiveTasks()
    }

    override fun onTask(task: Task) {
        val args = bundleOf("task" to task.toDto())
        findNavController().navigate(R.id.action_homeFragment_to_taskFragment, args)
    }

    override fun onOverflowMenu(task: Task, anchor: View) {
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

    override fun onDestroyView() {
        super.onDestroyView()

        _views = null
    }
}