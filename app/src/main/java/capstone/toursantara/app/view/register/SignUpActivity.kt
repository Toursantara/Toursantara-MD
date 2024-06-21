package capstone.toursantara.app.view.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import capstone.toursantara.app.R
import capstone.toursantara.app.view.login.LoginActivity

import okhttp3.*
import java.io.IOException

class SignUpActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        usernameEditText = findViewById(R.id.nameRegisterText)
        emailEditText = findViewById(R.id.EmailRegisterText)
        passwordEditText = findViewById(R.id.passwordRegisterText)
        val signupButton = findViewById<Button>(R.id.signupButton)

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            registerUser(username, email, password)
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        val requestBody = FormBody.Builder()
            .add("username", username)
            .add("email", email)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url("https://toursantara-api-gnqcrqbmxa-et.a.run.app/register")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Register failed", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Akun sudah berhasil dibuat", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Register failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
