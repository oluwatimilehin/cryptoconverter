package com.oluwatimilehin.cryptoconverter.conversion.di

import com.oluwatimilehin.cryptoconverter.conversion.ConversionActivity
import com.oluwatimilehin.cryptoconverter.data.di.ActivityScope
import dagger.Subcomponent

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ConversionModule::class))
interface ConversionComponent{
    fun inject(view: ConversionActivity)
}