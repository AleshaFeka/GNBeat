package self.edu.gnbeat.adapter

import self.edu.gnbeat.model.Track

interface TrackSelectedListener {
    fun onTrackSelected(trackIndex : Int)
}