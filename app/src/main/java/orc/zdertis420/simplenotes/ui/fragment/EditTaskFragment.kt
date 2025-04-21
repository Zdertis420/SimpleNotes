package orc.zdertis420.simplenotes.ui.fragment

import android.app.Application
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
import org.koin.core.logger.Logger

class EditTaskFragment : Fragment() {

    private var _views: FragmentEditTaskBinding? = null
    private val views get() = _views!!

    private val viewModel by viewModel<TaskViewModel>()

    private var task: Task? = null

    private val handler = Handler(Looper.getMainLooper())
    private val hideIndicator = Runnable {
        views.savedIndicator.visibility = View.GONE
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
            views.toolbar.title = getString(R.string.edit_task)
        }
    }

    private fun render(state: TaskState) {
        when (state) {
            is TaskState.Saved -> indicateSaved()
            is TaskState.Error.SavingError -> showSavingError()

            else -> {}
        }
    }

    private fun showSavingError() {
        Toast.makeText(context, getString(R.string.saving_error), Toast.LENGTH_SHORT).show()
    }

    private fun indicateSaved() {
        views.savedIndicator.visibility = View.VISIBLE
        handler.postDelayed(hideIndicator, 2500)
    }

    private fun saveTask() {
        Log.d("SAVE TASK", "fragment")
        viewModel.saveTask(
            name = views.taskName.text.toString(),
            category = views.taskCategory.text.toString(),
            description = views.taskDescription.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(hideIndicator)
        _views = null
    }
}