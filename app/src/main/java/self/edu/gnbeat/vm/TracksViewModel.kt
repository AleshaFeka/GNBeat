package self.edu.gnbeat.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import self.edu.gnbeat.di.DaggerMainComponent
import self.edu.gnbeat.di.MainModule
import self.edu.gnbeat.di.MethronomeApp
import self.edu.gnbeat.manager.TrackManager
import self.edu.gnbeat.model.Track
import javax.inject.Inject

class TracksViewModel : ViewModel() {
    @Inject lateinit var trackManager : TrackManager
    val selectedTrack : MutableLiveData<Int> = SingleLiveEvent()

    init {
        MethronomeApp.newsComponent.inject(this)
    }

    fun getAllTracks() : List<Track> =  trackManager.tracks

    fun onTrackClicked(position : Int) {
        selectedTrack.value = position
    }

    fun updateTrack(oldName : String, name: String, bpm: Int) {
        val track = trackManager.tracks.filter { it.name == oldName }.first()
        track.name = name
        track.bpm = bpm
    }

    fun registerTrack(track : Track) {
        trackManager.addTrack(track)
    }
}