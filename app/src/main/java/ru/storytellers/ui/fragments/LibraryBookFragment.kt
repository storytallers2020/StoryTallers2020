package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_library_book.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.LibraryBookViewModel

class LibraryBookFragment(private val story: Story):BaseFragment<DataModel>() {
    override val model: LibraryBookViewModel by inject()
    override val layoutRes= R.layout.fragment_library_book
    companion object {
        fun newInstance(story: Story) = LibraryBookFragment(story)
    }

    override fun init() {
        iniViewModel()

        model.getTextStory(story)
        model.getTitleStory(story)

        back_button_character.setOnClickListener {backClicked()}
    }

    override fun iniViewModel() {
        model.subscribeOnTextStory().observe(viewLifecycleOwner, Observer {textStory->
            textStory?.let {text-> tv_tale.text=text }
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, Observer {titleStory->
            titleStory?.let {title-> subHeader.text=title }
        })
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}