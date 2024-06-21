package capstone.toursantara.app.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import capstone.toursantara.app.view.MainActivity
import capstone.toursantara.app.R
import capstone.toursantara.app.utils.SharedPreferencesManager
import capstone.toursantara.app.view.register.SignUpActivity
import okhttp3.*
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.passwordEditText)
        val buttonLogin = findViewById<Button>(R.id.loginButton)
        buttonLogin.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginUser(email, password)
        }

        val buttonCreateAccount = findViewById<Button>(R.id.signUpButton)
        buttonCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String) {
        val requestBody = FormBody.Builder()
            .add("email", username)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url("https://toursantara-api-gnqcrqbmxa-et.a.run.app/login")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        SharedPreferencesManager.isLoggedIn = true
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}