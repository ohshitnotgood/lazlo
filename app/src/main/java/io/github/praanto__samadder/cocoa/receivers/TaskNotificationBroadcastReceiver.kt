package io.github.praanto__samadder.cocoa.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.praanto__samadder.cocoa.R
import io.github.praanto__samadder.cocoa.model.NotificationId
import io.github.praanto__samadder.cocoa.model.NotificationUtils
import io.github.praanto__samadder.cocoa.task_editor.TaskEditorActivity

class TaskNotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            val notificationDesc = getStringExtra("notificationDesc")!!
            val notificationTitle = getStringExtra("notificationTitle")!!
            val notificationId = getIntExtra("notificationId", NotificationId.PHYSICS_CLASS_REMINDER)
            val channelId = getStringExtra("channelId")!!

            val icon = when (notificationId) {
                NotificationId.PHYSICS_CLASS_REMINDER -> R.drawable.ic_atom__1_
                NotificationId.CHEMISTRY_CLASS_REMINDER -> R.drawable.ic_nucleus
                NotificationId.MATHS_CLASS_REMINDER -> R.drawable.ic_p3
                NotificationId.STATISTICS_CLASS_REMINDER -> R.drawable.ic_stats
                else -> R.drawable.ic_atom__1_
            }

            val pendingIntent = PendingIntent.getActivity(context, notificationId, Intent(context, TaskEditorActivity::class.java), 0)

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