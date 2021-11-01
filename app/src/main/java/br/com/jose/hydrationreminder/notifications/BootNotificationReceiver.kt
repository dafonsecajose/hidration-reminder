package br.com.jose.hydrationreminder.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class BootNotificationReceiver(
    private val notificationsSchedule: NotificationsSchedule
): BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            notificationsSchedule.createNotifications()
        }
    }
}