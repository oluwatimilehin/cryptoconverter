package com.oluwatimilehin.cryptoconverter.data.di

import com.oluwatimilehin.cryptoconverter.data.CardsDataSource
import com.oluwatimilehin.cryptoconverter.data.LocalCardsDataSource
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
    fun provideLocalCardsDataSource( localCardsDataSource: LocalCardsDataSource): CardsDataSource =
            localCardsDataSource
}