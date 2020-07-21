package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_location.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Location
import ru.storytellers.ui.fragments.LocationFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri

class LocationAdapter(val clickListener: OnListItemClickListener) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {
    private var locationList = mutableListOf<Location>()

    fun setData(dataListCharacters: List<Location>?) {
        dataListCharacters?.let {
            locationList.clear()
            locationList.addAll(it)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_location, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.bind(locationList[position])
    }

    // Kotlin style
    override fun getItemCount() = locationList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.locationDescription)
        private val imageView: ImageView = itemView.findViewById(R.id.locationView)

        fun bind(location: Location) {
            textView.text = location.name
            imageView.setImageResource(R.drawable.location_castle)
            resourceToUri(location.imageUrl)?.let {
                loadImage(it, itemView.locationView)
            }

            // Читаем подсказки студии
            imageView.setOnClickListener {
                val positionIndex: Int = getAdapterPosition()
                clickListener.onItemClick(locationList[positionIndex])
//                Snackbar.make(it, "Location " + positionIndex + " was choosen", Snackbar.LENGTH_LONG)
//                    .setDuration(3500)
//                    .show()
            }

            // Читаем подсказки студии
            itemView.setOnClickListener {
                val positionIndex: Int = getAdapterPosition()
                clickListener.onItemClick(locationList[positionIndex])
//                Snackbar.make(it, "Location " + positionIndex + " was choosen", Snackbar.LENGTH_LONG)
//                    .setDuration(3500)
//                    .show()
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(location: Location)
    }
}