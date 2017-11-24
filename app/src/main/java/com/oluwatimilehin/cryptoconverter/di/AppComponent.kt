package com.oluwatimilehin.cryptoconverter.di

import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.addcard.di.AddCardComponent
import com.oluwatimilehin.cryptoconverter.addcard.di.AddCardPresenterModule
import com.oluwatimilehin.cryptoconverter.cards.di.CardsListComponent
import com.oluwatimilehin.cryptoconverter.cards.di.CardsListModule
import com.oluwatimilehin.cryptoconverter.conversion.di.ConversionComponent
import com.oluwatimilehin.cryptoconverter.conversion.di.ConversionModule
import com.oluwatimilehin.cryptoconverter.data.di.ApiServiceModule
import com.oluwatimilehin.cryptoconverter.data.di.CardsRepositoryModule
import com.oluwatimilehin.cryptoconverter.data.di.DatabaseModule
import com.oluwatimilehin.cryptoconverter.utils.schedulers.SchedulerModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Singleton
@Component(modules = arrayOf(DatabaseModule::class, AppModule::class,
        ApiServiceModule::class, CardsRepositoryModule::class, SchedulerModule::class))
interface AppComponent{
    fun inject(application: App)

    fun plus(module: CardsListModule): CardsListComponent

    fun plus(module: ConversionModule): ConversionComponent

    fun plus(module: AddCardPresenterModule): AddCardComponent
}