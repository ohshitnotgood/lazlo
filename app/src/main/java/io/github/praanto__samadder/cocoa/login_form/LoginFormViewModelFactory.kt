package io.github.praanto__samadder.cocoa.login_form

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class LoginFormViewModelFactory(private val application: Application)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFormViewModel::class.java)) {
            return LoginFormViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}