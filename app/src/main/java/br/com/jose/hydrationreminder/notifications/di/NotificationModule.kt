package br.com.jose.hydrationreminder.notifications.di

import br.com.jose.hydrationreminder.notifications.BootNotificationReceiver
import br.com.jose.hydrationreminder.notifications.NotificationsSchedule
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object NotificationModule {
    fun load() {
        loadKoinModules(notificationScheduleModule())
    }

    private fun notificationScheduleModule(): Module {
        return module {
            factory { BootNotificationReceiver() }
            factory { NotificationsSchedule(androidApplication(), get()) }
        }
    }
}