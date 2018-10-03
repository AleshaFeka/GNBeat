package self.edu.gnbeat.vm

import android.arch.lifecycle.ViewModel
import self.edu.gnbeat.di.DaggerMainComponent
import self.edu.gnbeat.di.MainModule
import self.edu.gnbeat.manager.TrackManager
import self.edu.gnbeat.model.Track
import javax.inject.Inject

class TracksViewModel : ViewModel() {
    @Inject
    lateinit var trackManager : TrackManager

    init {
        DaggerMainComponent.builder()
                .mainModule(MainModule())
                .build()
                .inject(this)
    }

    fun getAllTracks() : List<Track> =  trackManager.tracks
}