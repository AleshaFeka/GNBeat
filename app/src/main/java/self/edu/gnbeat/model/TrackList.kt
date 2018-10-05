package self.edu.gnbeat.model

data class TrackList(
        var name : String = "NoNameList",
        var tracks : MutableList<Track> = mutableListOf()
)