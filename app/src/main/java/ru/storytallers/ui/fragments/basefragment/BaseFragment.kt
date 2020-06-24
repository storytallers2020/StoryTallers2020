package ru.storytallers.ui.fragments.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.storytallers.R
import ru.storytallers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel


abstract class BaseFragment<T:DataModel>: Fragment() {
 abstract val model: BaseViewModel<T>
  abstract val layoutRes: Int
 override fun onCreateView(
  inflater: LayoutInflater,
  container: ViewGroup?,
  savedInstanceState: Bundle?
 ): View? {
  return inflater.inflate(layoutRes, container, false)
 }
 }