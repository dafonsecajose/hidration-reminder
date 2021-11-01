package br.com.jose.hydrationreminder.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import br.com.jose.hydrationreminder.presentation.SettingsViewModel.Companion.EXTRA_NOTIFICATION_ID

import java.time.LocalDateTime

class NotificationsSchedule(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun schedule(date: LocalDateTime, id: Int){
        Log.i("noti", "schedule")

        setAlert(id, date, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlert(id: Int, date: LocalDateTime, flag: Int) {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(EXTRA_NOTIFICATION_ID, id)
        val pendingIntent = PendingIntent.getBroadcast(
            context, id, intent,
            flag
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            date.formatMilliSeconds(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun cancel(id: Int) {
        NotificationUtil.cancelAllNotification(context)
        setAlert(id, LocalDateTime.now(), PendingIntent.FLAG_CANCEL_CURRENT)
    }
}