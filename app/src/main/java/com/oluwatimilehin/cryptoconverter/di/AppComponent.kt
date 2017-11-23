package com.oluwatimilehin.cryptoconverter.di

import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.data.di.ApiServiceModule
import com.oluwatimilehin.cryptoconverter.data.di.CardsRepositoryModule
import com.oluwatimilehin.cryptoconverter.data.di.DatabaseModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Singleton
@Component(modules = arrayOf(DatabaseModule::class, AppModule::class,
        ApiServiceModule::class, CardsRepositoryModule::class))
interface AppComponent{
    fun inject(application: App)
}