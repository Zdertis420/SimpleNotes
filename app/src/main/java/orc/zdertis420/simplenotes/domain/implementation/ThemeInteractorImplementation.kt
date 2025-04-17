package orc.zdertis420.simplenotes.domain.implementation

import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor
import orc.zdertis420.simplenotes.domain.repository.ThemeRepository

class ThemeInteractorImplementation(private val themeRepository: ThemeRepository) : ThemeInteractor {
    override fun switchTheme(theme: Int) {
        themeRepository.switchTheme(theme)
    }

    override fun saveTheme(theme: Int) {
        themeRepository.saveTheme(theme)
    }

    override fun getTheme(): Int {
        return themeRepository.getTheme()
    }
}