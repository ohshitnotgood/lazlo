package io.github.praanto__samadder.cocoa.login_form

import android.app.Application
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.praanto__samadder.cocoa.dashboardActivity.DashboardActivity
import io.github.praanto__samadder.cocoa.fire.Auth
import io.github.praanto__samadder.cocoa.fire.UserID
import io.github.praanto__samadder.cocoa.model.SensitiveCredentials
import kotlinx.coroutines.*

/**
 * ViewModel class for LoginForm activity
 * @author Praanto Samadder
 */
class LoginFormViewModel (application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _isProgressBarVisible = MutableLiveData(false)
    val isProgressBarVisible : LiveData<Boolean> get() = _isProgressBarVisible


    /**
     * MutableLiveData that holds email. Should not be made private.
     */
    var userEmail = MutableLiveData("dip.dyan@beta.cocoa.io")

    /**
     * MutableLiveData thar holds password. Should not be made private.
     */
    var userPassword = MutableLiveData("tFAA!9^t'Fu6Ez}s")

    private val applicationContext = getApplication<Application>()


    /**
     * Executes click action for Login button
     */
    fun btnLoginClick() {
        val email = userEmail.value!!
        val password = userPassword.value!!

        val manufacturer = Build.MANUFACTURER
        val brand = Build.BRAND
        val board = Build.BOARD
        val buildId = Build.ID
        val device = Build.DEVICE
        val bootloader = Build.BOOTLOADER
        val product = Build.PRODUCT


        val cred = SensitiveCredentials(device = device, boardId = buildId, brand = brand,
            manufacturer = manufacturer, board = board, bootloader = bootloader, product = product)

        Auth.saveToCommon(cred)

        if (email.isNotEmpty() && password.isNotEmpty()) {
            ioScope.launch {
                btnLoginSignIn(email, password)
            }
        } else {
            Toast.makeText(applicationContext, "You missed some login details", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * + Suspendable, nullable function. Must be launched inside a [CoroutineScope].
     * + Checks if user credentials are valid.
     * + If [Auth.signInWithEmailPassword] returns an AuthResult, then login is successful and the
     * userID from the AuthResult is stored in sharedPreference and DashboardActivity is started.
     * + If [Auth.signInWithEmailPassword] return null, then login is unsuccessful. The user is notified
     * via [Toast] message.
     */
    private suspend fun btnLoginSignIn(email: String, password: String) {
        _isProgressBarVisible.postValue(true)
        val authResult = Auth.signInWithEmailPassword(email, password)

        if (authResult == null) {
            _isProgressBarVisible.postValue(false)
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext.baseContext, "Your email or password might have been incorrect.",
                    Toast.LENGTH_SHORT).show()
            }
        } else {
            withContext(Dispatchers.Main) {
                _isProgressBarVisible.postValue(false)
                ensureActive()
                UserID.saveUserID(authResult.user?.uid!!, applicationContext)
                val intent = Intent(applicationContext, DashboardActivity::class.java)
                intent.putExtra("fl", true)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                applicationContext.startActivity(intent)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}