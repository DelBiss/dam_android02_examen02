package ca.philrousse.android02.examen02.domain.repository

import ca.philrousse.android02.examen02.domain.model.ClientInfo
import ca.philrousse.android02.examen02.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun deleteClient(idc:Long)
    suspend fun getClientsList(ageMin:Long?=null, sex:String?=null):Flow<Resource<List<ClientInfo>>>
    suspend fun addClient(client:ClientInfo)
}