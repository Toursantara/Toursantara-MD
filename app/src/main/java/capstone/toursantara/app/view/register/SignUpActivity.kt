package capstone.toursantara.app.view.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import capstone.toursantara.app.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Pastikan layout ini benar

        val view = findViewById<View>(R.id.signupButton) // Ganti 'your_view_id' dengan ID yang benar
        if (view != null) {
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                // Handle insets
                insets
            }
        } else {
            Log.e("SignUpActivity", "View is null")
        }
    }
}