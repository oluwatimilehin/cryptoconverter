package com.oluwatimilehin.cryptoconverter.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Oluwatimilehin on 22/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Module
class AppModule(private val application: Application){

    @Provides @Singleton
    fun provideContext(): Context = application
}