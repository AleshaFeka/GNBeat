package self.edu.gnbeat.manager

import android.arch.lifecycle.MutableLiveData
import self.edu.gnbeat.model.Track
import self.edu.gnbeat.sound.BeatController

open class SoundManager (liveData: MutableLiveData<Track>, var beatController : BeatController) {

    init {
        liveData.observeForever {
            beatController.track = it
        }
    }

    fun toggleClick(){
        beatController.tickState = !beatController.tickState
    }
}





