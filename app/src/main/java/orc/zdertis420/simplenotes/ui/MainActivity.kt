package orc.zdertis420.simplenotes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.databinding.ActivityMainBinding
import orc.zdertis420.simplenotes.ui.fragment.HomeFragment
import orc.zdertis420.simplenotes.ui.fragment.MenuFragment
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    private lateinit var views: ActivityMainBinding

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

        views.homeToolbar.subtitle = LocalTime.now().toString()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment())
            }
        }

        views.button1.setOnClickListener {
            fragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container, MenuFragment())
                addToBackStack(null)
            }
        }

        views.button2.setOnClickListener {
            fragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container, HomeFragment())
                addToBackStack(null)
            }
        }
    }
}