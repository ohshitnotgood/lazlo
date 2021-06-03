package io.github.praanto__samadder.cocoa.login_form

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.boo_hoo.BooHooWhore
import io.github.praanto__samadder.cocoa.databinding.ActivityLoginFormBinding
import kotlinx.android.synthetic.main.activity_login_form.*

/**
 * LoginForm class holds the email and the password input textView
 * Implements MVVM data pass model
 * @author Praanto Samadder
 */
class LoginForm : AppCompatActivity() {

    private lateinit var binding : ActivityLoginFormBinding
    private lateinit var viewModel : LoginFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_form)

        val application = requireNotNull(this).application
        val viewModelFactory = LoginFormViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginFormViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.forgotPass.setOnClickListener {
            val intent = Intent(this, BooHooWhore::class.java)
            startActivity(intent)
        }

        viewModel.isProgressBarVisible.observe(this, {
            if (viewModel.isProgressBarVisible.value!!) {
                form.foreground = ColorDrawable(Color.parseColor("#8A000000"))
            } else {
                form.foreground = ColorDrawable(Color.TRANSPARENT)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }
}