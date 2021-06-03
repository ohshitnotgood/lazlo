package io.github.praanto__samadder.cocoa.main_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.praanto__samadder.cocoa.dashboardActivity.DashboardActivity
import io.github.praanto__samadder.cocoa.fire.UserID
import io.github.praanto__samadder.cocoa.login_form.WelcomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userID = UserID.fetchUserID(application)

        if (userID == "") {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
