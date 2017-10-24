package com.oluwatimilehin.cryptoconverter

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.oluwatimilehin.cryptoconverter.data.AppDatabase
import org.junit.After
import org.junit.Before

/**
 * Created by Oluwatimilehin on 24/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CurrencyDaoTest {

    var appDb: AppDatabase? = null;

    @Before
    fun initDb() {
        appDb = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java
        )
                .build()
    }

    @After
    fun closeDb(){
        appDb?.close()
    }
}