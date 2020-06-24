package ru.storytallers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.storytallers.ui.fragments.AuthFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthFragment.newInstance())
                    .commitNow()
        }
    }
}
