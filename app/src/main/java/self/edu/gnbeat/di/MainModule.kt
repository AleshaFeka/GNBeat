package self.edu.gnbeat.di

import android.arch.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import self.edu.gnbeat.manager.SoundManager
import self.edu.gnbeat.manager.TrackManager
import self.edu.gnbeat.model.Track
import self.edu.gnbeat.sound.BeatController
import javax.inject.Singleton

@Module
class MainModule {
    @Provides
    @Singleton
    fun provideTrackLiveData() : MutableLiveData<Track> {
        return MutableLiveData()
    }

    @Provides
    @Singleton
    fun provideTrackListLiveData() : MutableLiveData<List<Track>> {
        return MutableLiveData()
    }

    @Provides
    @Singleton
    fun provideSoundManager(mutableLiveData: MutableLiveData<Track>, beatController: BeatController) : SoundManager {
        return SoundManager(mutableLiveData, beatController)
    }

    @Provides
    @Singleton
    fun provideBeatController() : BeatController {
        return BeatController()
    }

    @Provides
    @Singleton
    fun provideTrackManager() : TrackManager {
        return TrackManager()
    }
}