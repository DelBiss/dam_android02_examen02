package ca.philrousse.android02.examen02.presentation.list

import ca.philrousse.android02.examen02.domain.model.ClientInfo

data class ClientListState(
    val clients: List<ClientInfo> = emptyList(),
    val isLoading:Boolean = false,
    val isRefreshing:Boolean = false
)

data class ClientListFilter(
    val sexFilter:String? = null,
    val ageFilter:Long? = null,
    var isApplied:Boolean = false
)
