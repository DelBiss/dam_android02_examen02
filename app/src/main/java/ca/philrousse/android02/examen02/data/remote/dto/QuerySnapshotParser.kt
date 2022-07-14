package ca.philrousse.android02.examen02.data.remote.dto

import com.google.firebase.firestore.QuerySnapshot

interface QuerySnapshotParser<T> {
    fun parse(snapshot: QuerySnapshot): List<T>
}