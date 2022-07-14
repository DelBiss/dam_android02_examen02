package ca.philrousse.android02.examen02.presentation.list


import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ca.philrousse.android02.examen02.databinding.FragmentFirstBinding
import ca.philrousse.android02.examen02.presentation.edit.SecondFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
private const val TAG = "FirstFragment"

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val vm: ClientListViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        hookRecycleView()
        binding.buttonFirst.setOnClickListener {
            DialogFilterSex{
                vm.onEvent(ClientListEvent.OnFilterSex(it))
                Log.d(TAG, "onViewCreated: $it")
            }.show(
                childFragmentManager,
                DialogFilterSex.TAG
            )
        }

        binding.buttonSecond.setOnClickListener {
            DialogNumberFragment("Entrez l'age minimum du client"){
                if(it>0){
                    vm.onEvent(ClientListEvent.OnFilterAge(it))
                } else{
                    vm.onEvent(ClientListEvent.OnFilterAge())
                }
                Log.d(TAG, "onViewCreated: Age $it")
            }.show(
                childFragmentManager,
                DialogFilterSex.TAG
            )
        }

        binding.buttonThird.setOnClickListener {
            DialogNumberFragment("Entrez l'id du client à supprimer"){
                vm.onEvent(ClientListEvent.DeleteClient(it))
                Log.d(TAG, "onViewCreated: $it")
            }.show(
                childFragmentManager,
                DialogFilterSex.TAG
            )
        }

        binding.fab.setOnClickListener {
            SecondFragment{
                vm.onEvent(ClientListEvent.OnNewClient(it))
            }.show(childFragmentManager,"newClient")
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hookRecycleView() {


        val recyclerView: RecyclerView = binding.recyclerView
        val clientAdapter = ClientAdapter()

        val mDividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayout.VERTICAL
        )
        recyclerView.addItemDecoration(mDividerItemDecoration)
        recyclerView.adapter = clientAdapter

        lifecycleScope.launch {
            vm.state.collect {

                if(it.clients.isNotEmpty()){
                    Log.d(TAG, "hookRecycleView: ${it.clients}")
                    clientAdapter.submitList(it.clients)
                } else{
                    clientAdapter.submitList(null)
                }
            }
        }



    }


}