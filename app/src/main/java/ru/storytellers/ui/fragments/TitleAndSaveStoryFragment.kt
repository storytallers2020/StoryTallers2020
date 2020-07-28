package ru.storytellers.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_choosing_title.*
import kotlinx.android.synthetic.main.item_image_cover.view.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.TitleAndSaveStoryViewModel

class TitleAndSaveStoryFragment:BaseFragment<DataModel>() {
    override val model: TitleAndSaveStoryViewModel by inject()
    override val layoutRes= R.layout.fragment_choosing_title

    companion object {
        fun newInstance() = TitleAndSaveStoryFragment()
    }
    private val textWatcher= object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(text: Editable) {
            if(text.toString().length>1){
                model.setTitleStory(text.toString())
            } else {
                context?.let { toastShowLong(it,"Enter title!") }
            }
        }
    }


    override fun init() {
        iniViewModel()
        book_name.addTextChangedListener(textWatcher)
    }

    override fun iniViewModel() {
        model.subscribeOnCover().observe(viewLifecycleOwner, Observer {
            resourceToUri(it.imageUrl)?.let {
                loadImage(it, iv_cover)
            }
        })
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}