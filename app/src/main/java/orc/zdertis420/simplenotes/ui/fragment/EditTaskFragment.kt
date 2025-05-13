package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.data.toTask
import orc.zdertis420.simplenotes.databinding.FragmentEditTaskBinding
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.ui.state.TaskState
import orc.zdertis420.simplenotes.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTaskFragment : Fragment() {

    private var _views: FragmentEditTaskBinding? = null
    private val views get() = _views!!

    private val viewModel by viewModel<TaskViewModel>()

    private var task: Task? = null

    private val handler = Handler(Looper.getMainLooper())
    private val finishEditing = Runnable {
        views.savedIndicator.visibility = View.GONE
        views.saveTask.isClickable = true
        findNavController().navigateUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentEditTaskBinding.inflate(inflater, container, false)

        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.post {
            task = arguments?.getParcelable("task", TaskDto::class.java)?.toTask()

            setupToolbar()

            if (task != null) {
                fillFields()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.taskStateFlow.collect { state ->
                        render(state)
                    }
                }
            }

            views.toolbar.setOnClickListener {
                findNavController().navigateUp()
            }

            views.saveTask.setOnClickListener {
                saveTask()
            }
        }
    }

    private fun setupToolbar() {
        if (task == null) {
            views.toolbar.title = getString(R.string.new_task)
        } else {
            views.toolbar.title = getString(R.string.task_editing)
        }
    }

    private fun fillFields() {
        views.taskName.setText(task!!.name)
        views.taskCategory.setText(task!!.category)
        views.taskDescription.setText(task!!.description)
    }

    private fun render(state: TaskState) {
        when (state) {
            is TaskState.Saved -> finish()
            else -> {}
        }
    }

    private fun finish() {
        views.saveTask.isClickable = false
        views.savedIndicator.visibility = View.VISIBLE
        handler.postDelayed(finishEditing, 750)
    }

    private fun saveTask() {
        Log.d("SAVE TASK", "fragment")
        if (task == null) {
            viewModel.addTask(
                views.taskName.text.toString(),
                views.taskCategory.text.toString(),
                views.taskDescription.text.toString()
            )
        } else {
            viewModel.updateTask(
                task!!,
                views.taskName.text.toString(),
                views.taskCategory.text.toString(),
                views.taskDescription.text.toString(),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(finishEditing)
        _views = null
    }
}