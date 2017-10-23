package com.oluwatimilehin.cryptoconverter.cards;

import com.oluwatimilehin.cryptoconverter.data.Card
import com.oluwatimilehin.cryptoconverter.data.Currency

/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

interface CardsContract {

    interface View{
        fun showProgressIndicator()
        fun onDatabaseUpdateSuccess()
        fun displayErrorMessage()
        fun onApiCallErrorResponse()
        fun updateRecyclerView(cards: kotlin.collections.List<Card>)
    }

    interface Presenter{
        fun loadDataFromApi()
        fun saveDataInDb(list: List<Currency>)
        fun loadDataFromDb()
        fun attachView(view: CardsContract.View)
        fun onDestroy()
    }
}
