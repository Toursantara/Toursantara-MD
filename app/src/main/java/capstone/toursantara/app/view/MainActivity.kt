package capstone.toursantara.app.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import capstone.toursantara.app.R
import capstone.toursantara.app.databinding.ActivityMainBinding
import capstone.toursantara.app.utils.SharedPreferencesManager
import capstone.toursantara.app.view.login.LoginActivity


class MainActivity : AppCompatActivity(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.elevation = 0f

        val drawerLayout = binding.drawerLayout
        val navView = binding.sideNavView
        val navController = findNavController(R.id.nav_host_fragment_container)

        appBarConfiguration = AppBarConfiguration(

            setOf(
                R.id.nav_home,
                R.id.recommendationActivity,
                R.id.nav_bookmarks

            ), drawerLayout

        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!SharedPreferencesManager.isUserLoggedIn()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.nav_home)
                    true
                }
                R.id.recommendationActivity -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.recommendationActivity)
                    true
                }
                R.id.nav_bookmarks -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.nav_bookmarks)
                    true
                }
                R.id.nav_logout -> {
                    logoutUser()
                    true
                }
                else -> false
            }
        }
    }

    private fun logoutUser() {
        SharedPreferencesManager.clearUserData()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return super.onSupportNavigateUp() || navController.navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen((GravityCompat.START))){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}