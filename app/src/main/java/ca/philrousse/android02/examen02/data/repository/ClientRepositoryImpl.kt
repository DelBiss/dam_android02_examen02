package ca.philrousse.android02.examen02.data.repository

import android.util.Log
import ca.philrousse.android02.examen02.data.mapper.toClientInfo
import ca.philrousse.android02.examen02.data.mapper.toClientInfoDocument
import ca.philrousse.android02.examen02.data.remote.ClientApi
import ca.philrousse.android02.examen02.domain.model.ClientInfo
import ca.philrousse.android02.examen02.domain.repository.ClientRepository
import ca.philrousse.android02.examen02.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ClientRepositoryImpl"
@Singleton
class ClientRepositoryImpl @Inject constructor(
    private val api: ClientApi
) : ClientRepository {
    override suspend fun deleteClient(idc: Long) {
        api.deleteClient(idc)
    }

    override suspend fun getClientsList(
        ageMin: Long?,
        sex: String?
    ): Flow<Resource<List<ClientInfo>>> {
        Log.d(TAG, "getClientsList: $ageMin, $sex")
        return try {
            api.getClientList(ageMin, sex).map { documentList ->
                val infoList = documentList.map { it.toClientInfo() }
                Resource.Success(infoList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            listOf(Resource.Error<List<ClientInfo>>("Couldn't load data")).asFlow()
        }
    }

    override suspend fun addClient(client: ClientInfo) {
        api.addClient(client.toClientInfoDocument())
    }


}