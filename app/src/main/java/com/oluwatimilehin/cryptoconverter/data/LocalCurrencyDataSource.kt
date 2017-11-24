package com.oluwatimilehin.cryptoconverter.data

import com.oluwatimilehin.cryptoconverter.data.daos.CurrencyDao
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 24/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class LocalCurrencyDataSource @Inject constructor(val currencyDao: CurrencyDao){

}