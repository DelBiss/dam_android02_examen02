package ca.philrousse.android02.examen02.presentation.list

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import ca.philrousse.android02.examen02.databinding.DialogueInputNumberBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


interface OnDialogResultListener {
    fun onDialogResult(result: String?)
}

class DialogFilterSex(private val callback:(String?)->Unit={}) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


            return AlertDialog.Builder(requireContext())
                .setMessage("Filtrez en fonction du genre")
                .setNegativeButton("Femme"){ _, _ -> callback("F")}
                .setNeutralButton("Sans Filtre") { _, _ -> callback(null)}
                .setPositiveButton("Homme") { _, _ -> callback("M")}
                .create()
    }

    companion object {
        const val TAG = "DialogFilterSex"
    }
}

class DialogNumberFragment(private val message:String,private val callback:(Long)->Unit={}) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dia = activity?.let {
            val b = DialogueInputNumberBinding.inflate(it.layoutInflater)
            AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setNegativeButton("Cancel"){ _, _ -> callback(-1L)}
                .setPositiveButton("Ok") { _, _ -> callback(b.editTextNumber.text.toString().toLong())}
                .setView(b.root)
        }?:AlertDialog.Builder(requireContext())


        return dia.create()
    }

    companion object {
        const val TAG = "DialogNumberFragment"
    }
}