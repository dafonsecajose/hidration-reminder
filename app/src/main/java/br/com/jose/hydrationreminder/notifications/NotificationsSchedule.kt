package br.com.jose.hydrationreminder.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatCallback
import androidx.lifecycle.*
import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.core.formatLocalDateTime
import br.com.jose.hydrationreminder.core.formatMilliSeconds
import br.com.jose.hydrationreminder.core.getDateString
import br.com.jose.hydrationreminder.domain.settings.GetSettingsUseCase
import br.com.jose.hydrationreminder.presentation.SettingsViewModel.Companion.EXTRA_NOTIFICATION_ID

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class NotificationsSchedule(
    private val context: Context,
    getSettingsUseCase: GetSettingsUseCase
) {
    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    private val settings: LiveData<Settings> = getSettingsUseCase.getSettings().asLiveData()
    private lateinit var beginDateTime: LocalDateTime
    private lateinit var finishedDateTime: LocalDateTime

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotifications() {
        val dateNow = getDateString()

        settings.value.let { settings ->
            beginDateTime = "$dateNow ${settings?.beginTime}:00".formatLocalDateTime()
            finishedDateTime = "$dateNow ${settings?.endTime}:00".formatLocalDateTime()
        }

        val differenceHour = ChronoUnit.HOURS.between(beginDateTime, finishedDateTime)

        var dateNotification = beginDateTime
        for (i in 0..differenceHour) {
            val date = when {
                dateNotification.isBefore(LocalDateTime.now()) -> dateNotification.plusDays(1)
                else -> dateNotification
            }

            Log.i("noti", date.toString())
            schedule(date, i.toInt())
            dateNotification = dateNotification.plusHours(1)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun schedule(date: LocalDateTime, id: Int){
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