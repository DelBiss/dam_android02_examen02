package ca.philrousse.android02.examen02.domain.model

import androidx.recyclerview.widget.DiffUtil

data class ClientInfo(
    var name:String,
    var adresse:String,
    var age:Long,
    var gender:String,
    val idc:Long?=null,
    val id:String?=""
){
    companion object {
        val CLIENT_COMPARATOR = object : DiffUtil.ItemCallback<ClientInfo>() {
            override fun areItemsTheSame(oldItem: ClientInfo, newItem: ClientInfo): Boolean =
                oldItem.idc == newItem.idc

            override fun areContentsTheSame(oldItem: ClientInfo, newItem: ClientInfo): Boolean =
                oldItem == newItem
        }
    }
}
