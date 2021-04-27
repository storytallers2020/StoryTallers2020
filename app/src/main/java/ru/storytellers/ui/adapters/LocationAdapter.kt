package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_location.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Location
import ru.storytellers.model.image.IImageLoader

class LocationAdapter(
    val imageLoader: IImageLoader,
    val clickListener: (location: Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {
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
        fun bind(location: Location) {
            imageLoader.loadInto(
                location.imageForRecycler,
                R.drawable.location_stub,
                itemView.locationView)

            itemView.setOnClickListener {
                clickListener(location)
            }
        }
    }
}