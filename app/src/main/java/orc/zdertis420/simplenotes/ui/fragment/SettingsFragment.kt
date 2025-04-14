package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import orc.zdertis420.simplenotes.databinding.FragmentSettingsBinding
import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor
import orc.zdertis420.simplenotes.ui.viewmodel.SettingsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _views: FragmentSettingsBinding? = null
    private val views get() = _views!!

    private val themeInteractor by inject<ThemeInteractor>()
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentSettingsBinding.inflate(inflater, container, false)

        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        views.switchTheme.isChecked = themeInteractor.getTheme()

        views.switchTheme.setOnCheckedChangeListener { switch, state ->
            viewModel.toggleTheme()
        }
    }
}