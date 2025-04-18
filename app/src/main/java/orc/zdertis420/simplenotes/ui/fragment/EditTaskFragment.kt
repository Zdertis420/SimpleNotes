package orc.zdertis420.simplenotes.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.data.toTask
import orc.zdertis420.simplenotes.databinding.FragmentEditTaskBinding
import orc.zdertis420.simplenotes.domain.entity.Task

class EditTaskFragment : Fragment() {

    private var _views: FragmentEditTaskBinding? = null
    private val views get() = _views!!

    private var task: Task? = null

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
            extractArgs()

            setupToolbar()

            views.toolbar.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun extractArgs() {
        if (arguments == null) {
            return
        } else {
            task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("task", TaskDto::class.java)
            } else {
                arguments?.get("task") as TaskDto
            }?.toTask()
        }
    }

    private fun setupToolbar() {
        if (task == null) {
            views.toolbar.title = getString(R.string.new_task)
        } else {
            views.toolbar.title = getString(R.string.edit_task)
        }
    }
}