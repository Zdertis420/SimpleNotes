package orc.zdertis420.simplenotes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.databinding.ActivityMainBinding
import orc.zdertis420.simplenotes.ui.adapter.PagerAdapter
import orc.zdertis420.simplenotes.ui.state.MainState
import orc.zdertis420.simplenotes.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var views: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel<MainViewModel>()

    private val fragmentManager = supportFragmentManager

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
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}