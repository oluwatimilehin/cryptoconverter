package com.oluwatimilehin.cryptoconverter.data

import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 14/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CurrencyRepository @Inject constructor(val remoteCurrencyDataSource:
                                             RemoteCurrencyDataSource, val localCardsDataSource: LocalCardsDataSource){

}