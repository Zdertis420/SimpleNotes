package orc.zdertis420.simplenotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import orc.zdertis420.simplenotes.ui.state.MainState
import java.time.LocalTime

class MainViewModel() : ViewModel() {
    private val _mainScreenLiveData = MutableLiveData<MainState>()
    val mainScreenLiveData: LiveData<MainState> get() = _mainScreenLiveData

    fun updateTime() {
        val now = LocalTime.now().hour

        _mainScreenLiveData.postValue(MainState.StartUp(now/6))
    }

    fun changeFragment() {

    }
}