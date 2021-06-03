package io.github.praanto__samadder.cocoa.login_form

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.model.LogMan
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * WelcomeActivity shows the Welcome text and the Continue button.
 * @author Praanto Samadder
 */

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val d : String = localClassName
        Toast.makeText(baseContext, d, Toast.LENGTH_SHORT).show()

        /* Starts new LoginForm activity when Continue button is pressed. */
        btnContinue.setOnClickListener {
            val intent = Intent(this, LoginForm::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}