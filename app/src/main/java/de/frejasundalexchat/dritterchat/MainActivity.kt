package de.frejasundalexchat.dritterchat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.frejasundalexchat.dritterchat.add_book.AddBookActivity
import de.frejasundalexchat.dritterchat.library.LibraryFragment
import de.frejasundalexchat.dritterchat.menu.CurrentFragment
import de.frejasundalexchat.dritterchat.menu.ProfileFragment
import de.frejasundalexchat.dritterchat.menu.StatsFragment

class MainActivity : AppCompatActivity() {

    private val CURRENT = "CURRENT"
    private val LIBRARY = "LIBRARY"
    private val PROFILE = "PROFILE"
    private val STATS = "STATS"

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.menuFragment.observe(this, Observer { menuFragment ->
            when (menuFragment) {
                MenuFragment.CurrentReadingCycle -> showCurrentFragment()
                MenuFragment.BookOverview -> showLibraryFragment()
                MenuFragment.Stats -> showStatsFragment()
                MenuFragment.Profile -> showSelfFragment()
            }
        })
        viewModel.showAddBookFragment.observe(this, Observer {
            showAddBook()
        })

        val menu = findViewById<BottomNavigationView>(R.id.menu)
        menu.selectedItemId = R.id.current

        menu.setOnNavigationItemSelectedListener { menuItem ->
            viewModel.menuItemClicked(menuItem.itemId)
        }
    }

    private fun showAddBook() {
        startActivity(Intent(this, AddBookActivity::class.java))
    }

    private fun showSelfFragment() {
        replaceFragment(ProfileFragment(), PROFILE)
    }

    private fun showStatsFragment() {
        replaceFragment(StatsFragment(), STATS)
    }

    private fun showLibraryFragment() {
        replaceFragment(LibraryFragment(), LIBRARY)
    }

    private fun showCurrentFragment() {
        replaceFragment(CurrentFragment(), CURRENT)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().replace(
            R.id.frame,
            fragment, tag
        ).commit()
    }
}