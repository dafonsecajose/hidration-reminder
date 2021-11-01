package br.com.jose.hydrationreminder.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.jose.hydrationreminder.presentation.SettingsViewModel.Companion.EXTRA_NOTIFICATION_ID

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)
        NotificationUtil.sendNotification(context, id.toLong())
    }
}