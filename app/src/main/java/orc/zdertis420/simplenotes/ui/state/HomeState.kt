package orc.zdertis420.simplenotes.ui.state

sealed class HomeState {
    data class StartUp(val partOfDate: Int) : HomeState()
}