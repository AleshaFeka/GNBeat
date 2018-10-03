package self.edu.gnbeat.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import self.edu.gnbeat.databinding.FragmentMethronomeBinding
import self.edu.gnbeat.vm.MethronomeViewModel

class MethronomeFragment : Fragment() {
    private lateinit var binding: FragmentMethronomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val viewModel = ViewModelProviders.of(activity!!).get(MethronomeViewModel::class.java)

        binding = FragmentMethronomeBinding.inflate(inflater)
        binding.model = viewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }
}
