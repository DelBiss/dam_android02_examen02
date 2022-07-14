package ca.philrousse.android02.examen02.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.philrousse.android02.examen02.domain.model.ClientInfo
import ca.philrousse.android02.examen02.domain.repository.ClientRepository
import ca.philrousse.android02.examen02.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientListViewModel @Inject internal constructor(
    private val repository: ClientRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ClientListState())
    val state = _state.asStateFlow()
    private val filter = MutableStateFlow(ClientListFilter())


    init {
        getClientList()
    }

    fun onEvent(event: ClientListEvent) {
        when (event) {
            is ClientListEvent.Refresh -> {
                getClientList()
            }
            is ClientListEvent.OnFilterSex -> {
                filter.value = ClientListFilter(sexFilter = event.query)
            }
            is ClientListEvent.OnFilterAge -> {
                filter.value = ClientListFilter(ageFilter = event.query)
            }
            is ClientListEvent.OnNewClient -> {
                addClient(event.client)
                getClientList()
            }
            is ClientListEvent.DeleteClient -> {
                deleteClient(event.idc)
                getClientList()
            }
        }
    }

    private fun deleteClient(idc:Long){
        viewModelScope.launch {
            repository.deleteClient(idc)
        }
    }
    private fun addClient(client:ClientInfo){
        viewModelScope.launch {
            repository.addClient(client)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getClientList() {
        viewModelScope.launch {
            filter.flatMapLatest { fil->
            repository
                .getClientsList(fil.ageFilter,fil.sexFilter)
            }.collect { result->
                when(result){
                    is Resource.Success -> {
                        result.data?.let{ clientList->
                            _state.value = _state.value.copy(
                                clients = clientList
                            )
                            filter.value.isApplied = true
                        }
                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }
}