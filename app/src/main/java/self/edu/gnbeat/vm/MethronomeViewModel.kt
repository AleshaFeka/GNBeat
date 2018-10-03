package self.edu.gnbeat.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import self.edu.gnbeat.di.DaggerMainComponent
import self.edu.gnbeat.di.MainModule
import self.edu.gnbeat.manager.SoundManager
import self.edu.gnbeat.manager.TrackManager
import self.edu.gnbeat.model.Track
import javax.inject.Inject

class MethronomeViewModel : ViewModel() {

    var track = Track()

    @Inject lateinit var currentTrackList : MutableLiveData<List<Track>>
    @Inject lateinit var trackManager : TrackManager
    @Inject lateinit var currentTrack : MutableLiveData<Track>
    @Inject lateinit var soundManager : SoundManager

    init {
        DaggerMainComponent.builder()
                .mainModule(MainModule())
                .build()
                .inject(this)
        currentTrack.value = track
        currentTrackList.value = trackManager.tracks
    }

    fun toggleClick() = updateCurrentTrack { soundManager.toggleClick() }

    fun up5Bpm() = changeBpm(5)

    fun down5Bpm() = changeBpm(-5)

    fun up1Bpm() = changeBpm(1)

    fun down1Bpm() = changeBpm(-1)

    private fun changeBpm (delta : Int)  =  updateCurrentTrack { track.bpm = track.bpm + delta }

    private fun updateCurrentTrack(change: () -> Unit) {
        change()
        currentTrack.value = track
    }
}

