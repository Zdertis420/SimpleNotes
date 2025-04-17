package orc.zdertis420.simplenotes.ui.state

sealed class SettingsState {
    data class Theme(val theme: Int) : SettingsState()
}