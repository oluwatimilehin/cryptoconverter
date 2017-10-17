package com.oluwatimilehin.cryptoconverter.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: Card)

    @Query("SELECT * from cards")
    fun getAllCards(): Flowable<List<Card>>

    @Query("UPDATE cards SET amount = :newAmount WHERE 'from' = :from AND 'to' = :to")
    fun updateAmount(newAmount: Double, from: String, to: String);

    @Query("DELETE from cards")
    fun deleteAllUsers();
}