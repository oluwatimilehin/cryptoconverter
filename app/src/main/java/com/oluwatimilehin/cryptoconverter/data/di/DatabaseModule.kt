package com.oluwatimilehin.cryptoconverter.data.di

import android.arch.persistence.room.Room
import android.content.Context
import com.oluwatimilehin.cryptoconverter.data.AppDatabase
import com.oluwatimilehin.cryptoconverter.utils.Constants
import com.oluwatimilehin.cryptoconverter.data.daos.CardDao
import com.oluwatimilehin.cryptoconverter.data.daos.CurrencyDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
const val DATABASE = "db"

@Module
class DatabaseModule{
    @Provides @Named(DATABASE)
    fun provideDatabaseName(): String = Constants.DB_NAME

    @Provides @Singleton
    fun provideDatabase(context: Context, @Named(DATABASE) dbName: String): AppDatabase{
       return  Room.databaseBuilder(context, AppDatabase::class.java, dbName)
               .addMigrations(AppDatabase.MIGRATION_2_3)
               .build()
    }

    @Provides @Singleton
    fun provideCardDao(appDatabase: AppDatabase): CardDao = appDatabase.cardDao()

    @Provides @Singleton
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao = appDatabase.currencyDao()
}