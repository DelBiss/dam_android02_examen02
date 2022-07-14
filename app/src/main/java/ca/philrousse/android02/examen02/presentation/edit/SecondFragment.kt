package ca.philrousse.android02.examen02.presentation.edit

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ca.philrousse.android02.examen02.databinding.FragmentSecondBinding
import ca.philrousse.android02.examen02.domain.model.ClientInfo
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment(private val callback:(ClientInfo)->Unit={}) : DialogFragment() {

//    private var _binding: FragmentSecondBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentSecondBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    private fun validateStringInput(edt: TextInputLayout): String? {
        val value = edt.editText!!.text.toString()
        if (value.isEmpty()) {
            edt.error = "Ce champs ne peut être vide"
            return null
        }
        return value
    }

    private fun validateLongInput(edt: TextInputLayout): Long? {
        val value = validateStringInput(edt)
        return value?.let {
            try {
                value.toLong()
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun getClientInfo(binding: FragmentSecondBinding): ClientInfo? {
        val age = validateLongInput(binding.txtAge)
        val adresse = validateStringInput(binding.txtAddresse)
        val nom = validateStringInput(binding.txtName)
        val genre = if (binding.radioButton1.isChecked) {"M"} else {"F"}

        Log.d(TAG, "getClientInfo: $age, $adresse, $nom, $genre")
        if (age != null && adresse != null && nom != null) {
            Log.d(TAG, "getClientInfo: Creating the client")
            return ClientInfo(
                name = nom,
                age = age,
                adresse = adresse,
                gender = genre
            )
        }
        return null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dia = activity?.let { activity ->
            val b = FragmentSecondBinding.inflate(activity.layoutInflater)
            b.buttonSecond.setOnClickListener {
                val result = getClientInfo(b)
                result?.also {
                    Log.d(TAG, "onCreateDialog: $it")
                    callback(it)
                    this.dismiss() }
            }

            AlertDialog.Builder(requireContext())
                .setMessage("Entrez les détail du client")
//                .setNegativeButton("Cancel"){ _, _ -> callback(-1L)}
//                .setPositiveButton("Ok") { _, _ -> callback(b.editTextNumber.text.toString().toLong())}
                .setView(b.root)
        } ?: AlertDialog.Builder(requireContext())


        return dia.create()
    }

    companion object {
        const val TAG = "DialogNumberFragment"
    }
}