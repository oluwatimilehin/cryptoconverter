package com.oluwatimilehin.cryptoconverter.data.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.oluwatimilehin.cryptoconverter.data.models.Card
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

    @Query("DELETE from cards where \"from\" = :from AND \"to\" = :to ")
    fun deleteCard(from: String, to:String)
}