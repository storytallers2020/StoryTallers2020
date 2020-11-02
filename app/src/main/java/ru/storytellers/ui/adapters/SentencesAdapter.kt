package ru.storytellers.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book_sentence.view.*
import ru.storytellers.R
import ru.storytellers.model.entity.SentenceOfTale

class SentencesAdapter(
    val itemClickListener: (itemView: View, position: Int) -> Unit?,
    val itemFocusListener: View.OnFocusChangeListener
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
        with(holder.itemView) {
            with(sentence) {
                isFocusable = true
                isFocusableInTouchMode = true
                onFocusChangeListener = itemFocusListener
            }

            isSelected = selectedPosition == position
        }

        holder.bind(listOfSentences[position])
    }

    inner class CCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sentenceOfTale: SentenceOfTale) {
            with(itemView) {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    sentence.setText(sentenceOfTale.content)
                    setOnClickListener { itemClickListener(itemView, layoutPosition) }
                }
            }
        }
    }
}