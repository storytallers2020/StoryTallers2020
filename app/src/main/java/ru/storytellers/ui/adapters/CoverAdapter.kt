package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_cover.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Cover
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri

class CoverAdapter(val clickListener: OnListItemClickListener) :
    RecyclerView.Adapter<CoverAdapter.MyViewHolder>() {
    private var coverList = mutableListOf<Cover>()

    fun setData(dataListCharacters: List<Cover>?) {
        dataListCharacters?.let {
            coverList.clear()
            coverList.addAll(it)
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
        myViewHolder.bind(coverList[position])
    }

    override fun getItemCount() = coverList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cover: Cover) {
            resourceToUri(cover.imagePreview)?.let {
                loadImage(it, itemView.book_cover_image)
            }

            itemView.book_cover_image.setOnClickListener {
                val positionIndex: Int = adapterPosition
                clickListener.onItemClick(coverList[positionIndex])
            }

            itemView.setOnClickListener {
                val positionIndex: Int = adapterPosition
                clickListener.onItemClick(coverList[positionIndex])
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(cover: Cover)
    }

}