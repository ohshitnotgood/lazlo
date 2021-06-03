package io.github.praanto__samadder.cocoa.fire

import android.app.Application
import android.content.Context

/**
 * @author Praanto Samadder
 */

object UserID {

    /**
     * Saves userID retrieved from Firebase Auth to SharedPreference.
     */
    fun saveUserID(userUid: String, application: Application) {
        application.getSharedPreferences("key_uid", Context.MODE_PRIVATE).edit()
            .putString("user_data_user_id", userUid).apply()
    }

    /**
     * Gets Firebase UserID from SharedPreference.
     */
    fun fetchUserID(application: Application) : String {
        return application.getSharedPreferences("key_uid", Context.MODE_PRIVATE)
            .getString("user_data_user_id", "")!!
    }

    /**
     * Saves last visited tab to SharedPreference.
     */
    fun saveLastUsedTab(lastTab: String, application: Application) {
        application.getSharedPreferences("last_visited_tab", Context.MODE_PRIVATE)
            .edit().putString("last_visited_tab", lastTab).apply()
    }

    /**
     * Gets last visited tab to SharedPreference.
     */
    fun fetchLastUserTab(application: Application) : String {
        return application.getSharedPreferences("last_visited_tab", Context.MODE_PRIVATE)
            .getString("last_visited_tab", "")!!

    }

}