package com.oluwatimilehin.cryptoconverter.cards.di

import com.oluwatimilehin.cryptoconverter.cards.CardsContract
import dagger.Module
import dagger.Provides

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Module
class CardsListModule(val view: CardsContract.View){
    @Provides
    fun provideCardsActivityView() = view
}