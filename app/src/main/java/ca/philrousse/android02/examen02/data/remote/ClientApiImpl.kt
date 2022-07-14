package ca.philrousse.android02.examen02.data.remote

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import ca.philrousse.android02.examen02.data.mapper.toHashMap
import ca.philrousse.android02.examen02.data.remote.dto.ClientInfoDocument
import ca.philrousse.android02.examen02.data.remote.dto.QuerySnapshotParser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "ClientApiImpl"
class ClientApiImpl @Inject constructor(
    private val ClientInfoParser:QuerySnapshotParser<ClientInfoDocument>
):ClientApi {
    private val db = FirebaseFirestore.getInstance().collection(ClientApi.BD_COLLECTION).document(ClientApi.BD_TABLE)
    override suspend fun deleteClient(idc: Long) {
        db.collection(ClientApi.BD_TABLE_COLLECTION).whereEqualTo("idc", idc).get().addOnSuccessListener { snapshot ->
            snapshot.forEach {
                it.reference.delete()
            }
        }
    }

    override suspend fun getClientList(
        ageMin: Long?,
        sex: String?
    ): Flow<List<ClientInfoDocument>> = callbackFlow {
        Log.d(TAG, "getClientList: $ageMin, $sex")
        val query = db.collection(ClientApi.BD_TABLE_COLLECTION)
            .let {
                ageMin?.let { ageMin->
                    it.whereGreaterThanOrEqualTo("age", ageMin)
                } ?:sex?.let { sexFilter->
                    it.whereEqualTo("sexe",sexFilter)
                } ?: it
            }


            val subscription = query.addSnapshotListener { value, error ->
                value?.let { snapshot ->
                    Log.d(TAG, "getClientList: Received Data")
                    trySend(ClientInfoParser.parse(snapshot).sortedBy {
                        it.idc
                    })
                }
            }

        awaitClose {subscription.remove()}
    }

    override suspend fun addClient(client:ClientInfoDocument){
        val a = client.toHashMap()
        getNextClientId().collect{
            a["idc"]=it
            incrementLastClientId(it)
            db.collection(ClientApi.BD_TABLE_COLLECTION)
                .add(a)
        }
    }
    private fun incrementLastClientId(idc:Long){
        db.set(
                hashMapOf("last_index" to idc)
            )
    }
    private suspend fun getNextClientId(): Flow<Long> = callbackFlow {
        db.get()
            .addOnSuccessListener {
                if(it.exists()){
                    trySend(1L + (it.getLong("last_index")?:-1L))
                } else {
                    trySend(-1L)
                }
            }
        awaitClose { }
    }
}