package ru.storytellers.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_choosing_title.*
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
                btn_next.isEnabled=true
            } else {
                context?.let { toastShowLong(it,"Enter title!") }
            }
        }
    }


    override fun init() {
        iniViewModel()
        book_name.addTextChangedListener(textWatcher)
        back_button_character.setOnClickListener { backClicked() }
        btn_next.setOnClickListener { saveStory() }
    }

    override fun iniViewModel() {
        model.subscribeOnCover().observe(viewLifecycleOwner, Observer {
            resourceToUri(it.imageUrl)?.let {
                loadImage(it, iv_cover)
            }
        })

        model.subscribeOnSuccessSaveFlag().observe(viewLifecycleOwner, Observer {
            if(it) activity?.let {
                    context -> toastShowLong(context,"Saved successfully") }
            else  activity?.let {
                    context -> toastShowLong(context,"Something went wrong")}
        })
    }
    private fun saveStory(){
        model.createStoryTaller()
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}