package com.oluwatimilehin.cryptoconverter.cards;

import com.oluwatimilehin.cryptoconverter.data.Card

/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

interface CardsContract {

    interface View{
        fun showEmptyCardsError(currenciesExist: Boolean)
        fun showEmptyCurrenciesError()
        fun currenciesExist()
        fun onDatabaseUpdateSuccess()
        fun showApiCallError()
        fun updateRecyclerView(cards: kotlin.collections.List<Card>)
        fun showAddCard()
        fun showCardDeleted()
    }

    interface Presenter{
        fun loadCurrencies()
        fun loadCards()
        fun attachView(view: CardsContract.View)
        fun onDestroy()
        fun addNewCard()
        fun deleteCard(card: Card)
    }
}
