package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

            viewModel.taskStateLiveData.observe(viewLifecycleOwner) { state ->
                render(state)
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
            is TaskState.Error.SavingError -> showSavingError()

            else -> {}
        }
    }

    private fun showSavingError() {
        Toast.makeText(context, getString(R.string.saving_error), Toast.LENGTH_SHORT).show()
    }

    private fun finish() {
        views.saveTask.isClickable = false
        views.savedIndicator.visibility = View.VISIBLE
        handler.postDelayed(finishEditing, 750)
    }

    private fun saveTask() {
        Log.d("SAVE TASK", "fragment")
        if (task == null) {
            viewModel.saveTask(
                name = views.taskName.text.toString(),
                category = views.taskCategory.text.toString(),
                description = views.taskDescription.text.toString()
            )
        } else {
            viewModel.updateTask(
                task!!,
                name = views.taskName.text.toString(),
                category = views.taskCategory.text.toString(),
                description = views.taskDescription.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(finishEditing)
        _views = null
    }
}