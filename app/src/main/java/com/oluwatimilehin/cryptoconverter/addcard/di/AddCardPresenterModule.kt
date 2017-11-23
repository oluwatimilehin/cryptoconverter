package com.oluwatimilehin.cryptoconverter.addcard.di

import com.oluwatimilehin.cryptoconverter.addcard.AddCardContract
import dagger.Module
import dagger.Provides

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Module
class AddCardPresenterModule(val view: AddCardContract.View){

    @Provides
    fun provideView(): AddCardContract.View = view
}