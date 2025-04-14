package orc.zdertis420.simplenotes.domain.repository

interface ThemeRepository {
    fun switchTheme(isDarkModeEnabled: Boolean)

    fun saveTheme(theme: Boolean)

    fun saveAutoTheme(theme: Boolean)

    fun getTheme(): Boolean

    fun isThemeEditedManually(): Boolean
}