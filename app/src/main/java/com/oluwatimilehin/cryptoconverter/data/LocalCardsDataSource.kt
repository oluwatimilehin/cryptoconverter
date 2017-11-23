package com.oluwatimilehin.cryptoconverter.data

import com.oluwatimilehin.cryptoconverter.data.daos.CardDao
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class LocalCardsDataSource public @Inject constructor(val cardDao: CardDao) : CardsDataSource{
    override fun saveCard() {

    }

    override fun getCards() {

    }

}