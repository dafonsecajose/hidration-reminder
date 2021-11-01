package br.com.jose.hydrationreminder.notifications

import br.com.jose.hydrationreminder.R
import br.com.jose.hydrationreminder.ui.MainActivity


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

object NotificationUtil {

    private const val PRIMARY_CHANNEL_ID = "primary_channel_id"

    fun sendNotification(context: Context, notificationId: Long){
        Log.i("noti", "util $notificationId",)
        val notificationManager = context
            .getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        createChannel(notificationManager, context)
        val notificationBuilder: NotificationCompat.Builder =
            getNotificationBuilder(context, pendingIntent)

        notificationManager.notify(notificationId.toInt(), notificationBuilder.build())
    }

    private fun getNotificationBuilder(context: Context, pendingIntent: PendingIntent?):
            NotificationCompat.Builder {
        return NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_drink)
            .setContentTitle(context.getString(R.string.notification_title))
            .setAutoCancel(true)
            .setContentText(context.getText(R.string.notification_content))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
    }

    private fun createChannel(notificationManager: NotificationManager, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Hidrate Notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.CYAN
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                context.getString(R.string.notification_channel_description)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun cancelAllNotification(context: Context){
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}