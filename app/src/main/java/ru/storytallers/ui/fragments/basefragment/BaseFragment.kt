package ru.storytallers.ui.fragments.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import ru.storytallers.ui.BackButtonListener
import ru.storytallers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


abstract class BaseFragment<T:DataModel>: Fragment(), BackButtonListener {
 abstract val model: BaseViewModel<T>
  abstract val layoutRes: Int
 lateinit var navigatorHolder: NavigatorHolder
  lateinit var router: Router
 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  injectRouter()
  return inflater.inflate(layoutRes, container, false)
 }

 private fun injectRouter(){
  val navigHold: NavigatorHolder by inject()
  navigatorHolder=navigHold
  val rout: Router by inject()
  router=rout
 }
 }