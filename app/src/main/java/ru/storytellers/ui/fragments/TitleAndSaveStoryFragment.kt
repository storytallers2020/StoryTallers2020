package ru.storytellers.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_choosing_title.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.MainActivity
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.TitleAndSaveStoryViewModel

class TitleAndSaveStoryFragment : BaseFragment<DataModel>() {
    override val model: TitleAndSaveStoryViewModel by inject()
    override val layoutRes = R.layout.fragment_choosing_title
    private lateinit var adMobFragment : AdMobFragment

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
                book_title.isErrorEnabled = false
            } else {
                btn_next.isEnabled = false
            }
        }
    }

    private val focusListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            hideSoftKey(v)
        }
    }

    override fun init() {
        book_name.addTextChangedListener(textWatcher)
        book_name.onFocusChangeListener = focusListener
        back_button_character.setOnClickListener { backToSelectCoverScreen() }
        btn_next.setOnClickListener { saveStory() }
    }

    override fun onStart() {
        super.onStart()
        iniViewModel()
        adMobFragment = AdMobFragment.newInstance(this)
        adMobFragment.buildAd()
    }

    override fun iniViewModel() {
        model.subscribeOnCover().observe(viewLifecycleOwner, Observer { cover ->
            resourceToUri(cover.imageUrl)?.let {
                loadImage(it, iv_cover)
            }
        })

        model.subscribeOnTitleAcceptable().observe(viewLifecycleOwner, Observer {
            if (!it) {
                book_title.error = context?.getString(R.string.enter_title)
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
    private fun saveStory() {
        model.saveStory()
    }

    private fun navigateToLibraryScreen() {
        val v: ConstraintLayout = requireActivity().findViewById(R.id.main_background)
        loadImage(R.drawable.ic_background_default, v)
        adMobFragment.startAd()
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