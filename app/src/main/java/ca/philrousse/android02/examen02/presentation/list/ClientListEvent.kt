package ca.philrousse.android02.examen02.presentation.list

import ca.philrousse.android02.examen02.domain.model.ClientInfo

sealed class ClientListEvent{
    object Refresh: ClientListEvent()
    data class OnFilterSex(val query: String? = null):ClientListEvent()
    data class OnFilterAge(val query: Long = 0):ClientListEvent()
    data class OnNewClient(val client: ClientInfo):ClientListEvent()
    data class DeleteClient(val idc:Long):ClientListEvent()
}
