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

    override fun switchTheme(isDarkModeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    override fun saveTheme(theme: Boolean) {
        themePreference.edit {
            putBoolean("THEME", theme)
            putBoolean("IS_EDITED_MANUALLY", true)
        }
    }

    override fun saveAutoTheme(theme: Boolean) {
        themePreference.edit {
            putBoolean("THEME", theme)
            putBoolean("IS_EDITED_MANUALLY", false)
        }
    }

    override fun getTheme(): Boolean {
        return themePreference.getBoolean("THEME", false)
    }

    override fun isThemeEditedManually(): Boolean {
        return themePreference.getBoolean("IS_EDITED_MANUALLY", false)
    }
}