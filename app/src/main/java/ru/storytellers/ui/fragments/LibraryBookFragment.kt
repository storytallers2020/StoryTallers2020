package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_library_book.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.setTextToClipboard
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.LibraryBookViewModel

class LibraryBookFragment(private val story: Story):BaseFragment<DataModel>() {
    override val model: LibraryBookViewModel by inject()
    override val layoutRes= R.layout.fragment_library_book
    companion object {
        fun newInstance(story: Story) = LibraryBookFragment(story)
    }

    override fun init() {
        back_button_character.setOnClickListener {backToLibraryScreen()}
        btn_copy.setOnClickListener { copyText() }
    }

    override fun onStart() {
        super.onStart()
        model.getTextStory(story)
        model.getTitleStory(story)
    }

    override fun onResume() {
        super.onResume()
        iniViewModel()
    }

    private fun copyText() {
        val res = tv_tale.text.toString().setTextToClipboard(requireContext())
        if (res) toastShowLong(requireContext(), getString(R.string.text_copied))
    }


    override fun iniViewModel() {
        model.subscribeOnTextStory().observe(viewLifecycleOwner, Observer {textStory->
            textStory?.let {text-> tv_tale.text=text }
        })

        model.subscribeOnTitleStory().observe(viewLifecycleOwner, Observer {titleStory->
            titleStory?.let {title-> subHeader.text=title }
        })
    }

    private fun backToLibraryScreen(){
        router.backTo(Screens.LibraryScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}