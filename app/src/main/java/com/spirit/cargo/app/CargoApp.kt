package com.spirit.cargo.app

import android.app.Application
import com.spirit.cargo.app.di.app
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import java.util.*

class CargoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            /*
           * We need to add logger Level
           * because it's koin problem, and it fix only in 2.2.0-beta-1, but it's unstable version
           * */
            androidLogger(Level.ERROR)
            androidContext(this@CargoApp)
            modules(app)
        }
    }
}
