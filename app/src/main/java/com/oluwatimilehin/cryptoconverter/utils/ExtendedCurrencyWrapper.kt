package com.oluwatimilehin.cryptoconverter.utils

import com.mynameismidori.currencypicker.ExtendedCurrency

/**
 * t
 *
 * Created by Oluwatimilehin on 04/12/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

/**
 * Wrapper to make it easier for testing
 */
class ExtendedCurrencyWrapper{
    fun getCurrencyByName(to: String): ExtendedCurrency{
        return ExtendedCurrency.getCurrencyByName(to)
    }
}