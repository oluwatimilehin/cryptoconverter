package com.oluwatimilehin.cryptoconverter.addcard.di

import com.oluwatimilehin.cryptoconverter.addcard.AddCard
import com.oluwatimilehin.cryptoconverter.data.di.ActivityScope
import dagger.Subcomponent

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(AddCardPresenterModule::class))
interface AddCardComponent {
    fun inject(activity: AddCard)
}