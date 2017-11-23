package com.oluwatimilehin.cryptoconverter.data

import com.oluwatimilehin.cryptoconverter.data.di.Local
import com.oluwatimilehin.cryptoconverter.data.models.Card
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 14/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsRepository public @Inject constructor(@Local private val localCardsDataSource: CardsDataSource){
      var localDataSource = localCardsDataSource

    fun saveCard(card: Card){

    }

    fun updateCard(card: Card, amount: Double){

    }

    fun getAllCards(): List<Card>{
        return emptyList()
    }
}