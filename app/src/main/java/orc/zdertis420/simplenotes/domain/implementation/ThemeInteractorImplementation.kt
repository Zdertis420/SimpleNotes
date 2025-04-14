package orc.zdertis420.simplenotes.domain.implementation

import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor
import orc.zdertis420.simplenotes.domain.repository.ThemeRepository

class ThemeInteractorImplementation(private val themeRepository: ThemeRepository) : ThemeInteractor {
    override fun switchTheme(isDarkModeEnabled: Boolean) {
        themeRepository.switchTheme(isDarkModeEnabled)
    }

    override fun saveTheme(theme: Boolean) {
        themeRepository.saveTheme(theme)
    }

    override fun saveAutoTheme(theme: Boolean) {
        themeRepository.saveAutoTheme(theme)
    }

    override fun getTheme(): Boolean {
        return themeRepository.getTheme()
    }

    override fun isThemeEditedManually(): Boolean {
        return themeRepository.isThemeEditedManually()
    }
}