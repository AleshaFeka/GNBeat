package self.edu.gnbeat.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import self.edu.gnbeat.di.DaggerMainComponent
import self.edu.gnbeat.di.MainModule
import self.edu.gnbeat.di.MethronomeApp
import self.edu.gnbeat.manager.SoundManager
import self.edu.gnbeat.manager.TrackManager
import self.edu.gnbeat.model.Track
import self.edu.gnbeat.model.TrackList
import javax.inject.Inject

class MethronomeViewModel : ViewModel() {
    @Inject lateinit var trackManager : TrackManager
    @Inject lateinit var currentTrack : MutableLiveData<Track>
    @Inject lateinit var soundManager : SoundManager

    private var trackList  = TrackList()
    private lateinit  var track : Track

    init {
        MethronomeApp.newsComponent.inject(this)

        trackList.tracks = trackManager.tracks
        changeTrack(0)

        currentTrack.value = track
    }

    fun onNextTrack(){
        val index = trackList.tracks.indexOf(track)

        if (index < (trackList.tracks.size - 1)) {
            changeTrack(index + 1)
        }
    }

    fun onPreviousTrack(){
        val index = trackList.tracks.indexOf(track)

        if (index > 0) {
            changeTrack(index - 1)
        }
    }

    fun up5Bpm() = changeBpm(5)

    fun down5Bpm() = changeBpm(-5)

    fun up1Bpm() = changeBpm(1)

    fun down1Bpm() = changeBpm(-1)

    fun changeTrack(index : Int) = updateCurrentTrack { track = trackManager.tracks[index] }

    fun toggleClick() = updateCurrentTrack { soundManager.toggleClick() }

    private fun changeBpm (delta : Int)  =  updateCurrentTrack { track.bpm = track.bpm + delta }

    private fun updateCurrentTrack(change: () -> Unit) {
        change()
        currentTrack.value = track
    }
}

