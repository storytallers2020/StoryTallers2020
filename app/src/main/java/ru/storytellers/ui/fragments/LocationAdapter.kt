package ru.storytellers.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.storytellers.R

class LocationAdapter(var dataList: List<String>?) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {
    private var list: List<String>? = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return MyViewHolder(view, list)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.bind(position)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class MyViewHolder(itemView: View, var dataList: List<String>?) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.locationDescription)
        private val imageView: ImageView = itemView.findViewById(R.id.locationView)

        fun bind(position: Int) {
            textView.text = dataList?.get(position)
            when (position % 2) {
                0 -> imageView.setImageResource(R.drawable.location_castle)
                1 -> imageView.setImageResource(R.drawable.location_cosmos)
            }

            imageView.setOnClickListener(View.OnClickListener {
                val positionIndex: Int = getAdapterPosition()
                Snackbar.make(it, "Location " + positionIndex + " was choosen", Snackbar.LENGTH_LONG)
                    .setDuration(3500)
                    .show()
            })

            itemView.setOnClickListener(View.OnClickListener {
                val positionIndex: Int = getAdapterPosition()
                Snackbar.make(it, "Location " + positionIndex + " was choosen", Snackbar.LENGTH_LONG)
                    .setDuration(3500)
                    .show()
            })
        }
    }
}