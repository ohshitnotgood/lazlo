package io.github.praanto__samadder.cocoa.dashboardActivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.praanto__samadder.cocoa.login_form.LoginFormViewModel

@Suppress("UNCHECKED_CAST")
class DashboardActivityViewModelFactory (private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardActivityViewModel::class.java)) {
            return DashboardActivityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}