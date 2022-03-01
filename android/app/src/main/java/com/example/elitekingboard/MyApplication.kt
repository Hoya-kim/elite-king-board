package com.example.elitekingboard

import android.app.Application
import androidx.databinding.ktx.BuildConfig
import com.example.elitekingboard.data.di.repositoryModule
import com.example.elitekingboard.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(listOf(viewModelModule, repositoryModule))
        }

    }
}
