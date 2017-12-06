package com.oluwatimilehin.cryptoconverter.utils

import com.mynameismidori.currencypicker.ExtendedCurrency
import javax.inject.Inject

/**
 * t
 *
 * Created by Oluwatimilehin on 04/12/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

/**
 * Wrapper to make it easier to mock ExtendedCurrency class
 */
class ExtendedCurrencyWrapper @Inject constructor(){

    fun getCurrencyByName(to: String): ExtendedCurrency{
        return ExtendedCurrency.getCurrencyByName(to)
    }
}