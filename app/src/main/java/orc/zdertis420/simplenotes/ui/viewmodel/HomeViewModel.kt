package orc.zdertis420.simplenotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import orc.zdertis420.simplenotes.ui.state.HomeState
import java.time.LocalTime

class HomeViewModel : ViewModel() {

    private val _homeStateLiveData = MutableLiveData<HomeState>()
    val homeStateLiveData: LiveData<HomeState> get() = _homeStateLiveData

    fun updateTime() {
        val now = LocalTime.now().hour

        _homeStateLiveData.postValue(HomeState.StartUp(now/6))
    }
}