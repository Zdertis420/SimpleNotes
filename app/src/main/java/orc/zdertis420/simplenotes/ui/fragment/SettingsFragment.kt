package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import orc.zdertis420.simplenotes.databinding.FragmentSettingsBinding
import orc.zdertis420.simplenotes.ui.viewmodel.SettingsViewModel
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.ui.state.SettingsState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _views: FragmentSettingsBinding? = null
    private val views get() = _views!!

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

        viewModel.screenStateLiveData.observe(viewLifecycleOwner) { state ->
            execute(state)
        }

        viewModel.updateTheme()
    }

    fun execute(state: SettingsState) {
        when (state) {
            is SettingsState.Theme -> updateThemes(state.theme)
        }
    }

    private fun updateThemes(theme: Int) {
        views.themeSelectorDropdownMenu.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.theme_dropdown_item,
                setThemeNames(theme)
            )
        )

        views.themeSelectorDropdownMenu.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.toggleTheme(setThemeIds(theme)[position])
                views.themeSelectorDropdownMenu.clearFocus()
                views.themeSelectorDropdownMenu.dismissDropDown()
            }
    }

    private fun setThemeNames(theme: Int): List<String> {
        return when (theme) {
            -1 -> listOf(
                getString(R.string.system_theme),
                getString(R.string.light_theme),
                getString(R.string.dark_theme),
            )

            1 -> listOf(
                getString(R.string.light_theme),
                getString(R.string.system_theme),
                getString(R.string.dark_theme),
            )

            2 -> listOf(
                getString(R.string.dark_theme),
                getString(R.string.system_theme),
                getString(R.string.light_theme),
            )

            else -> emptyList()
        }
    }

    private fun setThemeIds(theme: Int): List<Int> {
        return when (theme) {
            -1 -> listOf(-1, 1, 2)
            1 -> listOf(1, -1, 2)
            2 -> listOf(2, -1, 1)
            else -> emptyList()
        }
    }
}