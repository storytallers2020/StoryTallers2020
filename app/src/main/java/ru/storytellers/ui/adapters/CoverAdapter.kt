package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_cover.view.*
import kotlinx.android.synthetic.main.item_location.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Location
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri

class CoverAdapter(val clickListener: OnListItemClickListener) :
    RecyclerView.Adapter<CoverAdapter.MyViewHolder>() {
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
                .inflate(R.layout.item_image_cover, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.bind(locationList[position])
    }

    override fun getItemCount() = locationList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(location: Location) {
            resourceToUri(location.imageUrl)?.let {
                loadImage(it, itemView.book_cover_image)
            }

            itemView.book_cover_image.setOnClickListener {
                val positionIndex: Int = adapterPosition
                clickListener.onItemClick(locationList[positionIndex])
            }

            itemView.setOnClickListener {
                val positionIndex: Int = adapterPosition
                clickListener.onItemClick(locationList[positionIndex])
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(cover: Location)
    }

}