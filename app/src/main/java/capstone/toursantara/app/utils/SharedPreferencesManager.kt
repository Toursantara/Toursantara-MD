package capstone.toursantara.app.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val NAME = "SharedPreferencesManager"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val IS_LOGGED_IN = "is_logged_in"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    fun clearUserData() {
        preferences.edit().remove(IS_LOGGED_IN).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGGED_IN, false)
    }

    var isLoggedIn: Boolean
        get() = preferences.getBoolean(IS_LOGGED_IN, false)
        set(value) = preferences.edit().putBoolean(IS_LOGGED_IN, value).apply()

}
