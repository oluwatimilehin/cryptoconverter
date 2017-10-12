package com.oluwatimilehin.cryptoconverter.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Database(entities = arrayOf(Card::class, Currency::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cardDao(): CardDao;
    abstract fun currencyDao(): CurrencyDao
}