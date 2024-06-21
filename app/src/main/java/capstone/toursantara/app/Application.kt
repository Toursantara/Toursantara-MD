package capstone.toursantara.app

import android.app.Application
import capstone.toursantara.app.utils.SharedPreferencesManager
import com.google.firebase.FirebaseApp

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        SharedPreferencesManager.init(this)

    }
}