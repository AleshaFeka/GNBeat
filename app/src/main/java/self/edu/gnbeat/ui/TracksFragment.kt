package self.edu.gnbeat.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import self.edu.gnbeat.adapter.RecyclerViewTracksAdapter
import self.edu.gnbeat.adapter.TrackSelectedListener
import self.edu.gnbeat.databinding.FragmentTracksBinding
import self.edu.gnbeat.model.Track
import self.edu.gnbeat.vm.TracksViewModel

class TracksFragment : Fragment(), AddEditTrackDialog.AddEditTrackListener {

    private lateinit var binding: FragmentTracksBinding
    private lateinit var viewModel: TracksViewModel
    private var trackSelectedListener: TrackSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!).get(TracksViewModel::class.java)
        binding = FragmentTracksBinding.inflate(inflater)

        initRecyclerView()
        initFab()

        viewModel.selectedTrack.observe(this, Observer {
            it?.let {
                trackSelectedListener?.onTrackSelected(it)
            }
        })

        return binding.root
    }

    override fun onTrackCreated(name: String, bpm: Int) {
        viewModel.registerTrack(Track(name, bpm))
    }

    override fun onTrackEdited(oldName: String, name: String, bpm: Int) {
        viewModel.updateTrack(oldName, name, bpm)
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    private fun clickListener(index : Int) {
        viewModel.onTrackClicked(index)
    }

    private fun longClickListener(index : Int) {
        val track = viewModel.trackManager.tracks[index]
        val args = Bundle()
        args.putString(AddEditTrackDialog.ARG_NAME, track.name)
        args.putInt(AddEditTrackDialog.ARG_BPM, track.bpm)

        val dialog = AddEditTrackDialog()
        dialog.setTargetFragment(this, 0)
        dialog.arguments = args
        dialog.show(fragmentManager, "")
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            val dialog = AddEditTrackDialog()
            dialog.setTargetFragment(this, 0)
            dialog.show(fragmentManager, "")
        }
    }

    private fun initRecyclerView() {
        binding.recycler.layoutManager =  LinearLayoutManager(activity)
        binding.recycler.adapter = RecyclerViewTracksAdapter(viewModel.getAllTracks(),
                ::clickListener,
                ::longClickListener)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        trackSelectedListener = context as TrackSelectedListener
    }

    override fun onDetach() {
        super.onDetach()
        trackSelectedListener = null
    }
}