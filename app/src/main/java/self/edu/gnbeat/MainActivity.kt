package self.edu.gnbeat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import self.edu.gnbeat.ui.MethronomeFragment
import self.edu.gnbeat.ui.TrackListFragment
import self.edu.gnbeat.ui.TracksFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame_layout, MethronomeFragment())
                .commit()

        nav_view.setNavigationItemSelectedListener {
            var fragment : Fragment = when (it.itemId) {
                R.id.nav_tracks ->  TracksFragment()
                R.id.nav_tracklists ->  TrackListFragment()
                else -> MethronomeFragment()
            }


            drawer_layout.closeDrawers()

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment)
                    .addToBackStack("")
                    .commit()
            false
        }
    }
}
