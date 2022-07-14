package ca.philrousse.android02.examen02.data.remote.dto


import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientListParser @Inject constructor() :QuerySnapshotParser<ClientInfoDocument>{
    override fun parse(snapshot: QuerySnapshot): List<ClientInfoDocument> {
        return snapshot.map {
            ClientInfoDocument(
                age =it.getLong("age") ?: 0L,
                nom = it.getString("nom") ?: "",
                adresse = it.getString("adresse") ?: "",
                sexe = it.getString("sexe") ?: "",
                idc = it.getLong("idc") ?: 0L,
                id = it.id
            )
        }
    }

}
