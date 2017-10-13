package com.oluwatimilehin.cryptoconverter.cards;

import com.oluwatimilehin.cryptoconverter.data.Card

/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

interface CardsContract {

    interface View{
        fun showProgressIndicator()
        fun hideProgressIndicator()
        fun updateRecyclerView(cards: kotlin.collections.List<Card>)
    }

    interface Presenter{
        fun loadDataFromApi()
        fun saveDataInDb()
        fun loadDataFromDb()
        fun attachView(view: CardsContract.View)
        fun onDestroy()
    }
}
