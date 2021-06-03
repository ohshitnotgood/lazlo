package io.github.praanto__samadder.cocoa.boilerplate.login_forms

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import io.github.praanto__samadder.cocoa.R
import kotlinx.android.synthetic.main.activity_scene_two.*

/**
 * Allows user to enter email.
 *
 * When user presses Next, starts [SceneThreeActivity]
 */
class SceneTwoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_two)

        // Start Scene Three when user presses Enter
        uni_edt_email.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) startSceneThree(view.text.toString())
            return@setOnEditorActionListener true
        }

    }

    override fun finish() {
        overridePendingTransition(0, 0)
        super.finish()
    }

    private fun startSceneThree(email: String) {
        overridePendingTransition(0, 0)
        val intent = Intent(this, SceneThreeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION

        intent.putExtra("email", email)

        startActivity(intent)
        finish()
    }
}