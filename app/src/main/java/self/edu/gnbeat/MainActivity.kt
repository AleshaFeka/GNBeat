package self.edu.gnbeat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import self.edu.gnbeat.adapter.TrackSelectedListener
import self.edu.gnbeat.model.Track
import self.edu.gnbeat.ui.*

class MainActivity : FragmentActivity(), TrackSelectedListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            putStartFragment()
        }

        nav_view.setNavigationItemSelectedListener {
            var fragment : Fragment = when (it.itemId) {
                R.id.nav_tracks ->  TracksFragment()
                R.id.nav_tracklists ->  TrackListFragment()
                R.id.nav_settings -> SettingsFragment()
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

    override fun onTrackSelected(trackIndex: Int) {
        val args = Bundle()
        args.putInt(MethronomeFragment.ARG_POSITION, trackIndex)
        putStartFragment(args)
    }

    private fun putStartFragment(args : Bundle? = null) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val newFragment = MethronomeFragment()
        newFragment.arguments = args
        fragmentTransaction.replace(R.id.frame_layout, newFragment)
                .commit()
    }
}
