package orc.zdertis420.simplenotes.ui.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import orc.zdertis420.simplenotes.databinding.FragmentActiveBinding

class ActiveFragment : Fragment() {

    private var _views: FragmentActiveBinding? = null
    private val views get() = _views

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentActiveBinding.inflate(inflater, container, false)

        return views?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _views = null
    }
}