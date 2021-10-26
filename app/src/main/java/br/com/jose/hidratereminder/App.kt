package br.com.jose.hidratereminder

import android.app.Application
import br.com.jose.hidratereminder.data.di.DataModules
import br.com.jose.hidratereminder.domain.di.DomainModules
import br.com.jose.hidratereminder.presentation.di.PresentationModules
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
        PresentationModules.load()
    }


}