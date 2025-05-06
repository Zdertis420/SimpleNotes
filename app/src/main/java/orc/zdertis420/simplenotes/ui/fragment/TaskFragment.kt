package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.data.toTask
import orc.zdertis420.simplenotes.databinding.TaskBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TaskFragment : Fragment() {

    private var _views: TaskBinding? = null
    private val views get() = _views!!

    private val task by lazy { arguments?.getParcelable("task", TaskDto::class.java)!!.toTask() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = TaskBinding.inflate(inflater, container, false)

        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()

        views.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setup() = with(views) {
        namePlaceholder.text = task.name
        categoryPlaceholder.text = task.category
        createdPlaceholder.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.timestamp)
        descriptionPlaceholder.text = task.description
        idPlaceholder.text = task.id.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _views = null
    }
}