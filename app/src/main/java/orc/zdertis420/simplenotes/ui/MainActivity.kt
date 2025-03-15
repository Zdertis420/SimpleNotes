package orc.zdertis420.simplenotes.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orc.zdertis420.simplenotes.R

class MainActivity : AppCompatActivity() {

    private lateinit var text: TextView
    private lateinit var counter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        text = findViewById(R.id.text_view)
        counter = findViewById(R.id.counter)
        var count = counter.text.toString().toInt()

        text.setOnClickListener {
            ++count

            counter.text = count.toString()
        }

    }
}