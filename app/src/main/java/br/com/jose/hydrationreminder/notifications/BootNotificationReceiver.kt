package br.com.jose.hydrationreminder.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootNotificationReceiver: BroadcastReceiver(), KoinComponent {

    private val notificationsSchedule: NotificationsSchedule by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            notificationsSchedule.createNotifications()
        }
    }
}