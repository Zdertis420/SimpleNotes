package orc.zdertis420.simplenotes.ui.state

import java.time.LocalTime

sealed class MainState {
    data class StartUp(val partOfDay: LocalTime) : MainState()
}