package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_location.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Location
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri

class LocationAdapter(val clickListener: OnListItemClickListener) :
    RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {
    private var locationList = mutableListOf<Location>()

    fun setData(dataListCharacters: List<Location>?) {
        dataListCharacters?.let {
            locationList.clear()
            locationList.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_location, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.bind(locationList[position])
    }

    override fun getItemCount() = locationList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val textView: TextView = itemView.findViewById(R.id.locationDescription)  // это поле отсутствует в новом item_location
        private val imageView: ImageView = itemView.findViewById(R.id.locationView)

        fun bind(location: Location) {
//            textView.text = location.name
            resourceToUri(location.imageUrl)?.let {
                loadImage(it, itemView.locationView)
            }

            imageView.setOnClickListener {
                val positionIndex: Int = adapterPosition
                clickListener.onItemClick(locationList[positionIndex])
            }

            itemView.setOnClickListener {
                val positionIndex: Int = getAdapterPosition()
                clickListener.onItemClick(locationList[positionIndex])
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(location: Location)
    }

}