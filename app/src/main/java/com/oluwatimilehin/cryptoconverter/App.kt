package com.oluwatimilehin.cryptoconverter

import android.app.Application
import android.arch.persistence.room.Room
import com.facebook.stetho.Stetho
import com.oluwatimilehin.cryptoconverter.data.AppDatabase

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class App : Application(){

    companion object {
       lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this);

        App.database = Room.databaseBuilder(this, AppDatabase::class.java, "app-db")
                .addMigrations(AppDatabase.MIGRATION_2_3)
                .build()
    }
}