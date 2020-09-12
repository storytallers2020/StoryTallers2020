package ru.storytellers.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_choosing_title.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.TitleAndSaveStoryViewModel

class TitleAndSaveStoryFragment : BaseFragment<DataModel>() {
    override val model: TitleAndSaveStoryViewModel by inject()
    override val layoutRes = R.layout.fragment_choosing_title

    companion object {
        fun newInstance() = TitleAndSaveStoryFragment()
    }

    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(text: Editable) {
            if (text.toString().length > 1) {
                model.setTitleStory(text.toString())
                btn_next.isEnabled = true
            } else {
                context?.let { toastShowLong(it, it.getString(R.string.enter_title)) }
                btn_next.isEnabled = false
            }
        }
    }


    override fun init() {
        book_name.addTextChangedListener(textWatcher)
        back_button_character.setOnClickListener { backToSelectCoverScreen() }
        btn_next.setOnClickListener {
            saveStory()
        }
    }

    override fun onStart() {
        super.onStart()
        iniViewModel()
    }

    override fun iniViewModel() {
        model.subscribeOnCover().observe(viewLifecycleOwner, Observer { cover ->
            resourceToUri(cover.imageUrl)?.let {
                loadImage(it, iv_cover)
            }
        })

        model.subscribeOnSuccessSaveFlag().observe(viewLifecycleOwner, Observer {
            if (it) {
                activity?.let { context ->
                    toastShowLong(
                        context,
                        context.getString(R.string.msg_saved_successfully)
                    )
                }
                navigateToLibraryScreen()
            } else activity?.let { context ->
                toastShowLong(
                    context,
                    context.getString(R.string.something_went_wrong)
                )
            }
        })
    }
    private fun saveStory(){
        model.createStoryTaller()
    }

    private fun navigateToLibraryScreen() {
        router.navigateTo(Screens.LibraryScreen())
    }

    private fun backToSelectCoverScreen() {
        model.onBackClicked(this.javaClass.simpleName)
        router.backTo(Screens.SelectCoverScreen())
    }

    override fun backClicked(): Boolean {
        model.onBackClicked(this.javaClass.simpleName)
        router.exit()
        return true
    }
}