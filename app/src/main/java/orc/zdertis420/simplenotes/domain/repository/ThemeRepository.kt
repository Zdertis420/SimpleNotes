package orc.zdertis420.simplenotes.domain.repository

interface ThemeRepository {
    fun switchTheme(theme: Int)

    fun saveTheme(theme: Int)

    fun getTheme(): Int
}