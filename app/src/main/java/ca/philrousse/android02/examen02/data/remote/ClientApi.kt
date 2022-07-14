package ca.philrousse.android02.examen02.data.remote

import ca.philrousse.android02.examen02.data.remote.dto.ClientInfoDocument
import kotlinx.coroutines.flow.Flow


interface ClientApi {

    suspend fun deleteClient(idc:Long)
    suspend fun getClientList(ageMin:Long?=null, sex:String?=null): Flow<List<ClientInfoDocument>>
    suspend fun addClient(client:ClientInfoDocument)
    companion object{
        const val BD_COLLECTION = "bdclients"
        const val BD_TABLE = "clients"
        const val BD_TABLE_COLLECTION = "items"
    }
}