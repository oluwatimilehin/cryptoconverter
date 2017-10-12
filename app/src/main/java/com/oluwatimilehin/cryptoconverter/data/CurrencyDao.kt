package com.oluwatimilehin.cryptoconverter.data

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrencies(currencies: List<Currency>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAmounts(currencies: List<Currency>)

    @Query("SELECT amount from currencies WHERE from= :from AND to= :to")
    fun getConversionRate(from: String, to: String): Flowable<Double>;

}