package self.edu.gnbeat.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import self.edu.gnbeat.R

class AddEditTrackDialog : DialogFragment() {
    companion object {
        const val ARG_NAME = "argName"
        const val ARG_BPM = "argBpm"
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_add_edit_track, null)
            val editName = view.findViewById(R.id.name) as EditText
            val editBpm = view.findViewById(R.id.bpm) as EditText
            var isNewTrack = true

            arguments?.let {
                editName.setText(it.getString(ARG_NAME))
                editBpm.setText(it.getInt(ARG_BPM).toString())
                isNewTrack = false
            }

            val builder = AlertDialog.Builder(it)
            builder.setView(view)
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        val name = editName.text.toString()
                        val bpm = editBpm.text.toString().toInt()

                        val listener = targetFragment as AddEditTrackDialog.AddEditTrackListener
                        if (isNewTrack) {
                            listener.onTrackCreated(name, bpm)
                        } else {
                            listener.onTrackEdited(arguments!!.getString(ARG_NAME), name, bpm)}
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface AddEditTrackListener {
        fun onTrackEdited(oldName : String, newName : String, bpm : Int)
        fun onTrackCreated(name : String, bpm : Int)
    }
}