package io.github.praanto__samadder.cocoa.boilerplate.login_forms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.experimental.Experimental
import kotlinx.android.synthetic.main.activity_scene_four.*

/**
 * Animates Login Button to view when the user enter a password longer than 8 characters.
 */
class SceneFourActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene_four)

        // Get email from previous activity and set as email field text
        val email = intent.getStringExtra("email")
        uni_edt_email.setText(email)

        // Get password from previous activity and set as password field text
        val password = intent.getStringExtra("password")
        uni_edt_pass.setText(password)

        uni_edt_pass.addTextChangedListener(OnTextChanged())

        uni_btn_login.setOnClickListener {
            val intent = Intent(this, Experimental::class.java)
            startActivity(intent)
        }


    }

    inner class OnTextChanged : TextWatcher {
        override fun afterTextChanged(s: Editable?) { }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (uni_edt_pass.text.length > 8) animateLoginButton()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        /**
         * Animate login button to view after the user enter 8 characters in password field
         */
        private fun animateLoginButton(){
            ml_root_layout.apply {
                post { transitionToEnd() }
            }
        }
    }
}