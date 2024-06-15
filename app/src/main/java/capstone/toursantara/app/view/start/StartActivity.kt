package capstone.toursantara.app.view.start

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import capstone.toursantara.app.R
import capstone.toursantara.app.view.welcome.WelcomeActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val buttonGetStarted = findViewById<Button>(R.id.buttonInCardView)
        buttonGetStarted.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}