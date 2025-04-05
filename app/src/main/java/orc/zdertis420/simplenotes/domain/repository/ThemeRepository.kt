package orc.zdertis420.simplenotes.domain.repository

interface ThemeRepository {
    fun switchTheme(isDarkModeEnabled: Boolean)

    fun saveTheme(theme: Boolean)

    fun getTheme(): Boolean
}