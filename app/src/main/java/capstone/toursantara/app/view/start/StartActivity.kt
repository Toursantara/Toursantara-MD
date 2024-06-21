package capstone.toursantara.app.view.start

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import capstone.toursantara.app.R
import capstone.toursantara.app.utils.SharedPreferencesManager
import capstone.toursantara.app.view.MainActivity
import capstone.toursantara.app.view.login.LoginActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedPreferencesManager.isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_start)
        val buttonGetStarted = findViewById<Button>(R.id.buttonInCardView)
        buttonGetStarted.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}