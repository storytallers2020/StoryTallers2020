package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book_library.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.Story
import ru.storytellers.model.image.IImageLoader
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri

class LibraryAdapter(
    val imageLoader: IImageLoader,
    val itemClickListener: (story: Story) -> Unit,
    val btnMenuClickListener: (story: Story) -> Unit,
    val btnShareClickListener: () -> Unit,
    val btnCopyClickListener: () -> Unit,
    val btnDeleteClickListener: () -> Unit
) : RecyclerView.Adapter<LibraryAdapter.MyViewHolder>() {
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

    override fun getItemCount() = listStory.count()


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(story: Story) {
            with(itemView) {
                imageLoader.loadInto(story.coverUrl, R.drawable.cover_recycler_stub, book_cover_image)
//                resourceToUri(story.coverUrl)?.let {
//                    loadImage(it, book_cover_image)
//                }
                book_name.text = story.name

                setOnClickListener {
                    itemClickListener(story)
                }
                btn_menu.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        popup_menu.visibility = View.VISIBLE
                        btnMenuClickListener(story)
                    } else {
                        popup_menu.visibility = View.GONE
                    }
                }
                btn_share.setOnClickListener {
                    btnShareClickListener()
                }
                btn_copy.setOnClickListener {
                    btnCopyClickListener()
                }
                btn_delete.setOnClickListener {
                    btnDeleteClickListener()
                }
            }
        }
    }

}