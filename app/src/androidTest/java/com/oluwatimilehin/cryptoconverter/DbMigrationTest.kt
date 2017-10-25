package com.oluwatimilehin.cryptoconverter

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.arch.persistence.room.Room
import android.arch.persistence.room.testing.MigrationTestHelper
import android.support.test.InstrumentationRegistry
import com.oluwatimilehin.cryptoconverter.data.AppDatabase
import com.oluwatimilehin.cryptoconverter.data.Currency
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Created by Oluwatimilehin on 24/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class DbMigrationTest{

    val TEST_DB_NAME = "test-db"

    @Rule @JvmField
    public val testHelper: MigrationTestHelper = MigrationTestHelper(InstrumentationRegistry
            .getInstrumentation(), AppDatabase::class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory() )

    @Test
    fun migrationFrom2to3ContainsCorrectData(){

        var db: SupportSQLiteDatabase = testHelper.createDatabase(TEST_DB_NAME, 3)
        db.execSQL("insert into currencies('from', 'to', 'amount','drawable', 'selected', " +
                "'full_name') " +
                "values " +
                "('btc', " +
                "'ngn', 1000, 300, 1, 'dollars')")
        db.close()

       testHelper.runMigrationsAndValidate(TEST_DB_NAME, 3, true, AppDatabase.MIGRATION_2_3)

        val dbCurrency: Currency = getMigratedDatabase().currencyDao().getCurrencyByDetails("btc", "ngn")

        assertTrue(dbCurrency.amount.equals(1000))
    }

    fun getMigratedDatabase(): AppDatabase{
        val database: AppDatabase = Room.databaseBuilder(InstrumentationRegistry.getTargetContext
        (), AppDatabase::class.java, TEST_DB_NAME)
                .addMigrations(AppDatabase.MIGRATION_2_3)
                .build()

        testHelper.closeWhenFinished(database)

        return  database
    }

}