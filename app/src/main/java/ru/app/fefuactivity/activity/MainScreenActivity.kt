package ru.app.fefuactivity.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.app.fefuactivity.fragment.ActivityFragment
import ru.app.fefuactivity.fragment.ProfileFragment
import ru.app.fefuactivity.R

class MainScreenActivity : AppCompatActivity() {
    companion object {
        private const val TAG_ACTIVITY = "activity_fragment"
        private const val TAG_PROFILE = "profile_fragment"
    }

    private lateinit var fab: FloatingActionButton
    private lateinit var bottomNavigation: BottomNavigationView
    private var currentActivityTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        fab = findViewById(R.id.fab)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ActivityFragment(), TAG_ACTIVITY)
                .commit()
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> {
                    switchToFragment(TAG_ACTIVITY, ActivityFragment())
                    updateFabVisibility()
                    true
                }
                R.id.nav_profile -> {
                    switchToFragment(TAG_PROFILE, ProfileFragment())
                    fab.hide()
                    true
                }
                else -> false
            }
        }

        fab.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ru.app.fefuactivity.fragment.NewActivityFragment())
                .addToBackStack(null)
                .commit()
            fab.hide()
            bottomNavigation.visibility = View.GONE
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

    private fun updateFabVisibility() {
        if (currentActivityTab == 0) {
            fab.show()
        } else {
            fab.hide()
        }
    }

    fun onActivityTabChanged(tabPosition: Int) {
        currentActivityTab = tabPosition
        updateFabVisibility()
    }

    fun showDetailsFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        fab.hide()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            supportFragmentManager.executePendingTransactions()
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment is ru.app.fefuactivity.fragment.ProfileFragment ||
                currentFragment is ru.app.fefuactivity.fragment.ChangePasswordFragment) {
                fab.hide()
                bottomNavigation.visibility = View.VISIBLE
            } else if (currentFragment is ru.app.fefuactivity.fragment.NewActivityFragment) {
                fab.hide()
                bottomNavigation.visibility = View.GONE
            } else {
                updateFabVisibility()
                bottomNavigation.visibility = View.VISIBLE
            }
        } else {
            super.onBackPressed()
        }
    }
} 