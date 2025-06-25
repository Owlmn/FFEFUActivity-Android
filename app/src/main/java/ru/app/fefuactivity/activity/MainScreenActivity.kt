package ru.app.fefuactivity.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.app.fefuactivity.ActivityFragment
import ru.app.fefuactivity.fragment.ProfileFragment
import ru.app.fefuactivity.R

class MainScreenActivity : AppCompatActivity() {
    companion object {
        private const val TAG_ACTIVITY = "activity_fragment"
        private const val TAG_PROFILE = "profile_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ActivityFragment(), TAG_ACTIVITY)
                .commit()
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> {
                    switchToFragment(TAG_ACTIVITY, ActivityFragment())
                    true
                }
                R.id.nav_profile -> {
                    switchToFragment(TAG_PROFILE, ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun switchToFragment(tag: String, fragment: Fragment) {
        val fm = supportFragmentManager
        val activityFragment = fm.findFragmentByTag(TAG_ACTIVITY)
        val profileFragment = fm.findFragmentByTag(TAG_PROFILE)

        val transaction = fm.beginTransaction()

        activityFragment?.let { transaction.hide(it) }
        profileFragment?.let { transaction.hide(it) }

        var targetFragment = fm.findFragmentByTag(tag)
        if (targetFragment != null) {
            transaction.show(targetFragment)
        } else {
            transaction.add(R.id.fragment_container, fragment, tag)
        }
        transaction.commit()
    }

    fun showDetailsFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
} 