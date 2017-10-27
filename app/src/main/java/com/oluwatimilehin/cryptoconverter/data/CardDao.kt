package com.oluwatimilehin.cryptoconverter.data

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(card: Card)

    @Query("SELECT * from cards")
    fun getAllCards(): Single<List<Card>>

    @Query("UPDATE cards SET amount = :newAmount WHERE \"from\" = :from AND \"to\" = :to")
    fun updateAmount(newAmount: Double, from: String, to: String)

    @Query("SELECT * from cards WHERE \"from\" = :from AND \"to\" = :to")
    fun getCard(from: String, to: String): Flowable<Card>

    @Query("DELETE from cards")
    fun deleteAllCards()

    @Delete
    fun deleteCard(card: Card)
}