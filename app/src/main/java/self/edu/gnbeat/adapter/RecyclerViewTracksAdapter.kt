package self.edu.gnbeat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import self.edu.gnbeat.R
import self.edu.gnbeat.model.Track
import self.edu.gnbeat.vm.TracksViewModel

class RecyclerViewTracksAdapter(private var dataset:List<Track>,
                                val clickListener : (Int) -> Unit,
                                val longClickListener : (Int) -> Unit) : RecyclerView.Adapter<RecyclerViewTracksAdapter.TracksViewHolder>() {
    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): TracksViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.track, viewGroup, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(tracksViewHolder: TracksViewHolder, position: Int) {
        tracksViewHolder.trackName.text = dataset[position].name
        tracksViewHolder.trackBpm.text = "${dataset[position].bpm}"
    }

    inner class TracksViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val trackName : TextView = v.findViewById(R.id.trackName)
        val trackBpm : TextView = v.findViewById(R.id.trackBpm)

        init {
            v.setOnClickListener {
                clickListener(adapterPosition)
            }

            v.setOnLongClickListener {
               longClickListener(adapterPosition)
                true
            }
        }
    }
}