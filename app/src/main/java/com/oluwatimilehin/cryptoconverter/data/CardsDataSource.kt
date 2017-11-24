package com.oluwatimilehin.cryptoconverter.data

import com.oluwatimilehin.cryptoconverter.data.models.Card
import io.reactivex.Single

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface CardsDataSource{
    fun getCards(): Single<List<Card>>
    fun saveCard(card: Card)
    fun deleteCard(card: Card)
    fun deleteCards()
}