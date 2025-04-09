package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import orc.zdertis420.simplenotes.databinding.FragmentCompletedBinding

class CompletedFragment : Fragment() {

    private var _views: FragmentCompletedBinding? = null
    private val views get() = _views

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentCompletedBinding.inflate(inflater, container, false)

        return views?.root
    }
}