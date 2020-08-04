package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book_library.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Story
import ru.storytellers.utils.loadImage

class LibraryAdapter(
    val itemClickListener: ItemClickListener
): RecyclerView.Adapter<LibraryAdapter.MyViewHolder>() {
    private var listStory = mutableListOf<Story>()

    fun setData(localListStory: List<Story>?) {
        localListStory?.let {
            listStory.clear()
            listStory.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_book_library, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount()= listStory.count()


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(story:Story){
            loadImage(story.coverUrl,itemView.book_cover_image)
            itemView.book_name.text=story.name
            itemView.setOnClickListener {
                itemClickListener.onItemClick(story)
            }
        }

    }

    interface ItemClickListener {
        fun onItemClick(story: Story)
    }
}