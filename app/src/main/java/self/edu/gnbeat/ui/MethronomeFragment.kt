package self.edu.gnbeat.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import self.edu.gnbeat.databinding.FragmentMethronomeBinding
import self.edu.gnbeat.model.Track
import self.edu.gnbeat.vm.MethronomeViewModel

class MethronomeFragment : Fragment() {
    private lateinit var binding: FragmentMethronomeBinding

    companion object {
        const val ARG_POSITION = "argPosition"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val viewModel = ViewModelProviders.of(activity!!).get(MethronomeViewModel::class.java)
        var args = arguments

        args?.let {
            viewModel.changeTrack(args.getInt(ARG_POSITION))
            arguments = null
        }

        binding = FragmentMethronomeBinding.inflate(inflater)
        binding.model = viewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }
}
