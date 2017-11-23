package com.oluwatimilehin.cryptoconverter.cards.di

import com.oluwatimilehin.cryptoconverter.cards.CardsActivity
import com.oluwatimilehin.cryptoconverter.data.di.ActivityScope
import dagger.Subcomponent

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(CardsListComponent::class))
interface CardsListComponent {
    fun inject(view: CardsActivity)
}