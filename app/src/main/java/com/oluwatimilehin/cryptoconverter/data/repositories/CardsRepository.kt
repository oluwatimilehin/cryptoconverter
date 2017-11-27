package com.oluwatimilehin.cryptoconverter.data.repositories

import com.oluwatimilehin.cryptoconverter.data.di.Local
import com.oluwatimilehin.cryptoconverter.data.local.CardsDataManager
import com.oluwatimilehin.cryptoconverter.data.models.Card
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 14/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsRepository public @Inject constructor(@Local private val localCardsDataManager: CardsDataManager){
      var localDataSource = localCardsDataManager

    fun saveCard(card: Card){
        localCardsDataManager.saveCard(card)
    }

    fun updateCard(card: Card, amount: Double){
        localCardsDataManager.updateAmount(card, amount)
    }

    fun deleteCard(card: Card){
        localCardsDataManager.deleteCard(card)
    }

    fun deleteAll(){
        localCardsDataManager.deleteCards()
    }

    fun getAllCards(): Single<List<Card>> {
       return localCardsDataManager.getCards()
               .filter({it.isNotEmpty()})
               .toObservable()
               .singleOrError()
//               .flatMap { if(it.isEmpty()) return@flatMap  Single.error<List<Card>>(NoSuchElementException())
//                 return@flatMap Single.just(it)
//               }
    }
}