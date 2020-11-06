package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book_sentence.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.hideSoftKey

class SentencesAdapter(
    val btnClickListener: (itemView: View, position: Int) -> Unit?,
    val itemSelectedListener: (itemView: View, position: Int, hasFocus: Boolean) -> Unit?
) : RecyclerView.Adapter<SentencesAdapter.CCViewHolder>() {

    private var listOfSentences = mutableListOf<SentenceOfTale>()
    var selectedPosition = -1

    fun setData(dataListOfSentences: List<SentenceOfTale>?) {
        dataListOfSentences?.let {
            listOfSentences = it as MutableList<SentenceOfTale>
            notifyDataSetChanged()
            selectedPosition = -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CCViewHolder {
        return CCViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_book_sentence,
                    parent, false
                )
        )
    }

    override fun getItemCount() = listOfSentences.count()

    override fun onBindViewHolder(holder: CCViewHolder, position: Int) {
        holder.itemView.isSelected = selectedPosition == position
        holder.bind(listOfSentences[position])
    }

    inner class CCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sentenceOfTale: SentenceOfTale) {
            with (itemView) {
                sentence.setText(sentenceOfTale.content)
                sentence.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                    itemSelectedListener(this, layoutPosition, hasFocus)
                    if (hasFocus) {
                        sentence.setTextColor(ContextCompat.getColor(context, R.color.yellow))
                        btn_send.visibility = View.VISIBLE
                    } else {
                        sentence.setTextColor(ContextCompat.getColor(context, R.color.white))
                        btn_send.visibility = View.GONE
                        hideSoftKey(view)
                    }
                }
                btn_send.setOnClickListener { btnClickListener(this, layoutPosition) }
            }
        }
    }
}