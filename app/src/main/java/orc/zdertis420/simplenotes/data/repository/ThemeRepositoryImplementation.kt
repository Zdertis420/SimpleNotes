package orc.zdertis420.simplenotes.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import orc.zdertis420.simplenotes.domain.repository.ThemeRepository

class ThemeRepositoryImplementation(private val context: Context) : ThemeRepository {

    private val themePreference by lazy {
        context.getSharedPreferences("THEME_SETTING", MODE_PRIVATE)
    }

    override fun switchTheme(theme: Int) {
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    override fun saveTheme(theme: Int) {
        themePreference.edit {
            putInt("THEME", theme)
        }
    }

    override fun getTheme(): Int {
        return themePreference.getInt("THEME", -1)
    }
}