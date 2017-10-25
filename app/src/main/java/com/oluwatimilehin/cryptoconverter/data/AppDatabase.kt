package com.oluwatimilehin.cryptoconverter.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Database(entities = arrayOf(Card::class, Currency::class), version = 3)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cardDao(): CardDao;
    abstract fun currencyDao(): CurrencyDao

    companion object {
        val MIGRATION_2_3: Migration  = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS cards_new (`uid` INTEGER PRIMARY " +
                        "KEY AUTOINCREMENT NOT NULL, `currency_name` TEXT NOT NULL, `from` TEXT NOT NULL," +
                        " `to` TEXT NOT NULL, `amount` REAL NOT NULL, `flag` INTEGER NOT NULL)")

                database.execSQL("DROP TABLE cards")

                database.execSQL("ALTER TABLE cards_new RENAME TO cards")
            }

        }
    }
}