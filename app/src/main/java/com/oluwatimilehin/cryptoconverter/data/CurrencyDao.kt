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
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrencies(currencies: List<Currency>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(currency: Currency)

    @Query("SELECT * from currencies where 'from' = :from and 'to' = :to")
    fun getCurrencyByDetails(from: String, to: String): Currency

    @Query("UPDATE currencies SET 'selected' = :selected, 'drawable' = :drawable, 'full_name' = " +
            ":fullName" +
            " WHERE 'from' = :from AND 'to' = :to ")
    fun updateCurrency(selected: Int, drawable: Int, fullName: String, from: String, to: String)

    @Query("SELECT * from currencies WHERE 'selected' = 1")
    fun getAllCards(): Flowable<List<Currency>>
}