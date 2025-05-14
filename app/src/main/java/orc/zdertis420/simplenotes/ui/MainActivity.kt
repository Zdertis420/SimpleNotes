package orc.zdertis420.simplenotes.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orc.zdertis420.simplenotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _views: ActivityMainBinding? = null
    private val views get() = _views!!

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _views = null
    }
}