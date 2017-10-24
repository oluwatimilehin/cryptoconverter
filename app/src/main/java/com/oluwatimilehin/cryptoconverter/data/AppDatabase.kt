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
    abstract fun currencyDao(): CurrencyDao;

    companion object {
        var MIGRATION_2_3: Migration = object: Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE currencies ADD COLUMN drawable INTEGER")
                database.execSQL("ALTER TABLE currencies ADD COLUMN selected INTEGER")
                database.execSQL("ALTER TABLE currencies ADD COLUMN full_name STRING")
            }
        }
    }
}