package self.edu.gnbeat.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import self.edu.gnbeat.databinding.FragmentTracksBinding
import self.edu.gnbeat.vm.TracksViewModel

class TracksFragment : Fragment() {
    private lateinit var binding : FragmentTracksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewModel = ViewModelProviders.of(activity!!).get(TracksViewModel::class.java)

        viewModel.getAllTracks()

        binding = FragmentTracksBinding.inflate(inflater)
        return binding.root
    }
}