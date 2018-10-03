package self.edu.gnbeat.di

import dagger.Component
import self.edu.gnbeat.vm.MethronomeViewModel
import self.edu.gnbeat.vm.TracksViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(methronomeViewModel: MethronomeViewModel)
    fun inject(tracksViewModel: TracksViewModel)
}