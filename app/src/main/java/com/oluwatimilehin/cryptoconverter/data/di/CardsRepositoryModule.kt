package com.oluwatimilehin.cryptoconverter.data.di

import com.oluwatimilehin.cryptoconverter.data.local.CardsDataManager
import com.oluwatimilehin.cryptoconverter.data.local.LocalCardsDataManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Module
class CardsRepositoryModule{

    @Provides
    @Singleton
    @Local
    fun provideLocalCardsDataSource(localCardsDataManager: LocalCardsDataManager): CardsDataManager =
            localCardsDataManager
}