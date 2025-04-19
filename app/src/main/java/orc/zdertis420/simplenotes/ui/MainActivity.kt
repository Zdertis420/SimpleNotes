package orc.zdertis420.simplenotes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _views: ActivityMainBinding? = null
    private val views get() = _views!!

    val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _views = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(views.root)
        ViewCompat.setOnApplyWindowInsetsListener(views.mainFragmentContainer) { view, windowInsetsCompat ->
            val insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        val navHostFragment = fragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
    }

    override fun onDestroy() {
        super.onDestroy()
        _views = null
    }
}