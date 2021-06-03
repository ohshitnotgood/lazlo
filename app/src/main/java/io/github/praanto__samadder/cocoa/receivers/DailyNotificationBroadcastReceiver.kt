package io.github.praanto__samadder.cocoa.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.dashboardActivity.DashboardActivity
import io.github.praanto__samadder.cocoa.model.NotificationId
import io.github.praanto__samadder.cocoa.model.NotificationUtils

class DailyNotificationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.apply {
            val notificationDesc = getStringExtra("notificationDesc")!!
            val notificationTitle = getStringExtra("notificationTitle")!!
            val notificationId = getIntExtra("notificationId", NotificationId.DAILY_HOMEWORK_REMINDER)
            val channelId = getStringExtra("channelId")!!

            val icon = R.drawable.ic_physics_n

            val pendingIntent = PendingIntent.getActivity(context, notificationId, Intent(context, DashboardActivity::class.java), 0)

            NotificationUtils.showNotification(
                context = context!!,
                notificationId = notificationId,
                notificationDesc = notificationDesc,
                notificationTitle = notificationTitle,
                channelID = channelId,
                pendingIntent = pendingIntent,
                icon = icon
            )
        }
    }
}