package com.oluwatimilehin.cryptoconverter

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho
import com.oluwatimilehin.cryptoconverter.data.AppDatabase
import com.oluwatimilehin.cryptoconverter.di.AppComponent
import com.oluwatimilehin.cryptoconverter.di.AppModule
import com.oluwatimilehin.cryptoconverter.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class App : Application(){

    companion object {
       lateinit var database: AppDatabase
    }
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        Stetho.initializeWithDefaults(this)

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        App.database = Room.databaseBuilder(this, AppDatabase::class.java, "app-db")
                .addMigrations(AppDatabase.MIGRATION_2_3)
                .build()
    }
}