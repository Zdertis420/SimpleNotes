package orc.zdertis420.simplenotes.domain.interactor

interface ThemeInteractor {
    fun switchTheme(theme: Int)

    fun saveTheme(theme: Int)

    fun getTheme(): Int
}