package orc.zdertis420.simplenotes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orc.zdertis420.simplenotes.databinding.ActivityMainBinding
import orc.zdertis420.simplenotes.ui.state.MainState
import orc.zdertis420.simplenotes.ui.viewmodel.MainViewModel
//import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    private lateinit var views: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        views = ActivityMainBinding.inflate(layoutInflater)
        setContentView(views.root)
        ViewCompat.setOnApplyWindowInsetsListener(views.main) { view, windowInsetsCompat ->
            val insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

//        startKoin {
//            androidContext(this@MainActivity)
//
//            modules(viewModelModule)
//        }


        viewModel.mainScreenLiveData.observe(this) { state ->
            render(state)
        }

        viewModel.updateTime()
    }

    private fun render(state: MainState) {
        when (state) {
            is MainState.StartUp -> changeGreeting(state.partOfDay)
        }
    }

    private fun changeGreeting(partOfDay: LocalTime) {
        views.toolbar.subtitle = partOfDay.toString()
    }
}