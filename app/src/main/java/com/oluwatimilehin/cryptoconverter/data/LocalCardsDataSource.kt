package com.oluwatimilehin.cryptoconverter.data

import com.oluwatimilehin.cryptoconverter.data.daos.CardDao
import com.oluwatimilehin.cryptoconverter.data.models.Card
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class LocalCardsDataSource public @Inject constructor(val cardDao: CardDao) : CardsDataSource{
    override fun saveCard(card: Card) {
        cardDao.insert(card)
    }

    override fun deleteCard(card: Card) {
        cardDao.deleteCard(card.from, card.to)
    }

    override fun deleteCards() {
        cardDao.deleteAllCards()
    }

    override fun getCards(): Single<List<Card>> = cardDao.getAllCards()

}