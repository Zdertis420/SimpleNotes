package orc.zdertis420.simplenotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import orc.zdertis420.simplenotes.domain.interactor.ThemeInteractor
import orc.zdertis420.simplenotes.ui.state.SettingsState

class SettingsViewModel(
    private val themeInteractor: ThemeInteractor,
) : ViewModel() {

    private val _screenStateLiveData = MutableLiveData<SettingsState>()
    val screenStateLiveData: LiveData<SettingsState> get() = _screenStateLiveData

    fun toggleTheme(theme: Int) {
        try {
            themeInteractor.switchTheme(theme)
            themeInteractor.saveTheme(theme)
        } catch (e: Exception) {
            Log.e("ERROR", "INVALID THEME INDEX $theme\n$e")
        }

        Log.d("THEME", "THEME SET $theme")
    }

    fun updateTheme() {
        _screenStateLiveData.postValue(SettingsState.Theme(themeInteractor.getTheme()))
    }
}