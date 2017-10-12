package com.oluwatimilehin.cryptoconverter

import android.app.Application
import android.arch.persistence.room.Room
import com.oluwatimilehin.cryptoconverter.data.AppDatabase

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class App : Application(){

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        App.database = Room.databaseBuilder(this, AppDatabase::class.java, "app-db")
                .build()
    }
}