package self.edu.gnbeat.di

import android.app.Application

class MethronomeApp : Application() {
    companion object {
        lateinit var newsComponent: MainComponent
    }
    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerMainComponent.builder()
                .mainModule(MainModule())
                .build()
    }
}