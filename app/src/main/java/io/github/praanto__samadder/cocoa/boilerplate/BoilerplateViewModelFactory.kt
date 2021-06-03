package io.github.praanto__samadder.cocoa.boilerplate

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.praanto__samadder.cocoa.login_form.LoginFormViewModel

@Suppress("UNCHECKED_CAST")
class BoilerplateViewModelFactory(private val application: Application)
    : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BoilerplateViewModel::class.java)) {
            return LoginFormViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class BoilerplateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = BoilerplateViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[BoilerplateViewModel::class.java]
    }
}