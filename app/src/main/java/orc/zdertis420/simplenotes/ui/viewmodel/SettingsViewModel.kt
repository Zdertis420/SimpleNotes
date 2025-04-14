package orc.zdertis420.simplenotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor

class SettingsViewModel(
    private val themeInteractor: ThemeInteractor
) : ViewModel() {
    fun toggleTheme() {
        val newTheme = !themeInteractor.getTheme()

        themeInteractor.switchTheme(newTheme)
        themeInteractor.saveTheme(newTheme)
    }
}