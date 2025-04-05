package orc.zdertis420.simplenotes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayoutMediator
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.databinding.ActivityMainBinding
import orc.zdertis420.simplenotes.ui.adapter.PagerAdapter
import orc.zdertis420.simplenotes.ui.fragment.HomeFragment
import orc.zdertis420.simplenotes.ui.state.MainState
import orc.zdertis420.simplenotes.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var views: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel<MainViewModel>()

    private val fragmentManager = supportFragmentManager
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        views = ActivityMainBinding.inflate(layoutInflater)
        setContentView(views.root)
        ViewCompat.setOnApplyWindowInsetsListener(views.mainActivity) { view, windowInsetsCompat ->
            val insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }


        viewModel.mainScreenLiveData.observe(this) { state ->
            render(state)
        }

        viewModel.updateTime()

        views.viewPager.adapter = PagerAdapter(fragmentManager, lifecycle)

        tabLayoutMediator = TabLayoutMediator(views.tabLayout, views.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Home"
                1 -> tab.text = "Menu"
            }
        }

        tabLayoutMediator.attach()
    }

    private fun render(state: MainState) {
        when (state) {
            is MainState.StartUp -> changeGreeting(state.partOfDay)
        }
    }

    private fun changeGreeting(partOfDay: Int) {
        when (partOfDay) {
            0 -> views.homeToolbar.subtitle = getString(R.string.greeting_night)
            1 -> views.homeToolbar.subtitle = getString(R.string.greeting_morning)
            2 -> views.homeToolbar.subtitle = getString(R.string.greeting_day)
            3 -> views.homeToolbar.subtitle = getString(R.string.greeting_evening)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}