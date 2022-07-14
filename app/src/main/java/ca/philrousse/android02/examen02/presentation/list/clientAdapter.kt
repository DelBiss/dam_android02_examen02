package ca.philrousse.android02.examen02.presentation.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.philrousse.android02.examen02.R
import ca.philrousse.android02.examen02.databinding.ItemBinding
import ca.philrousse.android02.examen02.domain.model.ClientInfo

private const val TAG = "clientAdapter"
class ClientAdapter :
    ListAdapter<ClientInfo, ClientAdapter.ClientViewHolder>(ClientInfo.CLIENT_COMPARATOR){

        class ClientViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            private var binding: ItemBinding
            private var context: Context

            init {
                binding = ItemBinding.bind(itemView)
                context  = itemView.context
            }

            fun bind(item: ClientInfo){
                Log.d(TAG, "bind: $item")
                binding.data = item
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val produit = getItem(position)
        holder.bind(produit)
    }
}
