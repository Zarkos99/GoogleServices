package sweng888.project.googleservices

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import sweng888.project.googleservices.fragments.AddItemFragment
import sweng888.project.googleservices.fragments.DeleteItemFragment
import sweng888.project.googleservices.fragments.ItemListFragment


class MainNavigationActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var m_drawer_layout: DrawerLayout
    private lateinit var m_navigation_view: NavigationView
    private var m_action_bar_drawer_toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        m_drawer_layout = findViewById(R.id.nav_drawer_layout)
        m_navigation_view = findViewById(R.id.nav_view)

        /** Set the listener for the NavigationView. The Main Activity should
         * implement the interface NavigationView.OnNavigationItemSelectedListener  */
        m_navigation_view.setNavigationItemSelectedListener(this)

        /** Set up the ActionBarDrawerToggle  */
        m_action_bar_drawer_toggle = ActionBarDrawerToggle(
            this,  // Activity / Context
            m_drawer_layout,  // DrawerLayout
            R.string.navigation_drawer_open,  // String to open
            R.string.navigation_drawer_close // String to close
        )
        /** Include the m_action_bar_drawer_toggle as the listener to the DrawerLayout.
         * The syncState() method is used to synchronize the state of the navigation drawer  */
        m_drawer_layout.addDrawerListener(m_action_bar_drawer_toggle!!)
        m_action_bar_drawer_toggle!!.syncState()

        /** Set the default fragment  */
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ItemListFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_item_list -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ItemListFragment()).commit()

            R.id.nav_create_item -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddItemFragment()).commit()

            R.id.nav_delete_item -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DeleteItemFragment()).commit()
        }
        /** Close the navigation drawer  */
        m_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /** Inflate the menu; this adds items to the action bar if it is present.  */
        menuInflater.inflate(R.menu.nav_drawer_items, menu)
        return true
    }

}