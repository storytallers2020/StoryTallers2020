package ru.storytellers.ui.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_start_about.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.ui.BackButtonListener
import ru.terrakok.cicerone.Router

class AppInfoFragment : Fragment(), BackButtonListener {
    private val router: Router by inject()
    companion object {
        fun newInstance() = AppInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button_about.setOnClickListener { backClicked() }
        setAllLinksClickable(view)
    }

    private fun setAllLinksClickable(v: View): ArrayList<View>? {
        if (v !is ViewGroup) {
            return ArrayList<View>().apply { add(v) }
        }
        return ArrayList<View>().apply {
            for (i in 0 until v.childCount) {
                val child = v.getChildAt(i)
                if (child is TextView) {
                    child.movementMethod = LinkMovementMethod.getInstance()
                }
                ArrayList<View>().apply {
                    add(v)
                    addAll(setAllLinksClickable(child)!!)
                    addAll(this)
                }
            }
        }
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}