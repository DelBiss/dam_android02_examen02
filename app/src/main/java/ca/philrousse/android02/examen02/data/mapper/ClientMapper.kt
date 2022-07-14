package ca.philrousse.android02.examen02.data.mapper

import ca.philrousse.android02.examen02.data.remote.dto.ClientInfoDocument
import ca.philrousse.android02.examen02.domain.model.ClientInfo

fun ClientInfoDocument.toClientInfo(): ClientInfo {
    return ClientInfo(
        name = nom,
        adresse = adresse,
        age = age,
        gender = sexe,
        idc = idc,
        id = id
    )
}

fun ClientInfoDocument.toHashMap(): MutableMap<String, Any> {
    return mutableMapOf(
        "nom" to nom,
        "adresse" to adresse,
        "age" to age,
        "sexe" to sexe
    )
}

fun ClientInfo.toClientInfoDocument(): ClientInfoDocument{
    return ClientInfoDocument(
        nom = name,
        adresse = adresse,
        age = age,
        sexe = gender,
        idc = idc?:-1L,
        id = id?:""
    )
}