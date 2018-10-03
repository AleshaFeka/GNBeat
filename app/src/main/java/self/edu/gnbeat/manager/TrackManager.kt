package self.edu.gnbeat.manager

import self.edu.gnbeat.model.Track
import self.edu.gnbeat.model.TrackList
import javax.inject.Inject

class TrackManager {
    val tracks = mutableListOf<Track>()
    val trackLists = mutableListOf<TrackList>()

    init {
        loadTracks()
        loadTrackLists()
    }

    private fun loadTracks() {
        for (i : Int in 0..14) {
            tracks.add(Track(name = "Track_$i", bpm = 100 + (i * 3)))
        }

    }

    private fun loadTrackLists() {
        for (i : Int in 0..4) {
            trackLists.add(TrackList(name = "TrackList_$i", tracks = tracks.subList(i * 3, (i + 1) * 3)))
        }
    }

    fun getDefaultTrack() : Track {
        return Track()
    }

}