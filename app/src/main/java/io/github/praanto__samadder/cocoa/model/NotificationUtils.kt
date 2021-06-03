package io.github.praanto__samadder.cocoa.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import io.github.praanto__samadder.cocoa.R

object NotificationUtils {

    fun createNotificationChannel(context: Context, channelID: String, channelName: String, channelDescription: String) {

        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val newChannel = NotificationChannel(channelID, channelName, importance).apply{
            description = channelDescription
            enableLights(true)
            enableVibration(true)
            setShowBadge(true)
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(newChannel)
    }

    fun showNotification(context: Context,
                         notificationTitle: String, notificationDesc: String, icon: Int,
                         pendingIntent: PendingIntent, channelID: String, notificationId: Int
    ) {
        val notificationBuilder = NotificationCompat.Builder(context, channelID).apply {
            setAutoCancel(true)
            setContentTitle(notificationTitle)
            setContentText(notificationDesc)
            setSmallIcon(icon)
            setColorized(true)
            setChannelId(channelID)
            setContentIntent(pendingIntent)
            color = context.getColor(R.color.UIKitBlue)
        }
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notificationBuilder.build())
        }
    }

}

object ChannelID {
    const val DAILY_CLASS_REMINDER  = "dailyClassReminder"
    const val HOMEWORK_REMINDER = "homeworkReminder"
}

object ChannelName {
    const val DAILY_CLASS_REMINDER  = "Daily Class Reminder"
    const val HOMEWORK_REMINDER  = "Homework Reminder"
}

object NotificationId {
    const val DAILY_HOMEWORK_REMINDER       = 0
    const val PHYSICS_CLASS_REMINDER        = 1
    const val STATISTICS_CLASS_REMINDER     = 2
    const val MATHS_CLASS_REMINDER          = 3
    const val CHEMISTRY_CLASS_REMINDER      = 4
}
