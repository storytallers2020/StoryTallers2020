package ru.storytellers.ui.assistant

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.sentence_input_layout.*
import ru.storytellers.ui.fragments.GameFragment

class GameFragmentAssistant(
  private val gameFragment: GameFragment
) {
    fun hideKeyboard(){
    (gameFragment.inputMethodManager as? InputMethodManager)?.
    hideSoftInputFromWindow(gameFragment.sentence_line.windowToken,
      InputMethodManager.HIDE_NOT_ALWAYS)
  }

  fun makeEditextActive(editTextView: TextInputEditText, textInputLayout: TextInputLayout) {
    editTextView.isEnabled = true
    textInputLayout.alpha = 1f
  }

   private fun makeVisibleBtnSend(){
       gameFragment.btn_send.visibility= View.VISIBLE
   }

    fun makeInVisibleBtnSend(){
        gameFragment.btn_send.visibility= View.GONE
    }

    fun showIntro() {
       gameFragment.reminder_intro.visibility = View.VISIBLE
        gameFragment.game_field.visibility = View.GONE
    }
     fun showGameField() {
        gameFragment.reminder_intro.visibility = View.GONE
        gameFragment.game_field.visibility = View.VISIBLE
    }

    fun getTextWatcher():TextWatcher{
        return  object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length<2) makeInVisibleBtnSend()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(text: Editable) {
                if (text.toString().length>2) {
                    gameFragment.textSentenceOfTale = text.toString()
                    makeVisibleBtnSend()
                }
            }
        }
    }

}