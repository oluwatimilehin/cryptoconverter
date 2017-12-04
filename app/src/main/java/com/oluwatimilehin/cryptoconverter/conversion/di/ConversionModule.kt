package com.oluwatimilehin.cryptoconverter.conversion.di

import com.oluwatimilehin.cryptoconverter.conversion.ConversionContract
import dagger.Module
import dagger.Provides

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Module
class ConversionModule(val view: ConversionContract.View){
    @Provides
    fun provideView(): ConversionContract.View = view
}