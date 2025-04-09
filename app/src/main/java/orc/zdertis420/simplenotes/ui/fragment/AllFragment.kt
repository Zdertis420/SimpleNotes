package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import orc.zdertis420.simplenotes.databinding.FragmentAllBinding

class AllFragment : Fragment() {

    private var _views: FragmentAllBinding? = null
    private val views get() = _views

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentAllBinding.inflate(inflater, container, false)

        return views?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _views = null
    }
}