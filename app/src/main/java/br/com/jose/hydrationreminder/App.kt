package br.com.jose.hydrationreminder

import android.app.Application
import br.com.jose.hydrationreminder.data.di.DataModules
import br.com.jose.hydrationreminder.domain.di.DomainModules
import br.com.jose.hydrationreminder.notifications.di.NotificationModule
import br.com.jose.hydrationreminder.presentation.di.PresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
        }
        DataModules.load()
        DomainModules.load()
        NotificationModule.load()
        PresentationModules.load()
    }


}