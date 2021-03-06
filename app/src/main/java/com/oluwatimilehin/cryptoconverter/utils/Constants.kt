package com.oluwatimilehin.cryptoconverter.utils

import android.text.TextUtils
import com.mynameismidori.currencypicker.ExtendedCurrency

/**
 * Created by Oluwatimilehin on 19/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

/**
 * All the app constants used in various classes
 */
class Constants{

    companion object {

        val BASE_URL = "https://min-api.cryptocompare.com/"
        val DB_NAME = "app-db"

        val KEY_FROM = "from"
        val KEY_TO = "to"
        val KEY_AMOUNT = "amount"

        //Currencies used in API call.
        private val listOfCurrencies: List<String> = listOf("EUR",
                "USD",
                "GBP",
                "CZK",
                "TRY",
                "JPY",
                "NGN",
                "AED",
                "AFN",
                "ARS",
                "AUD",
                "BDT",
                "BGN",
                "BHD",
                "BND",
                "BOB",
                "BRL",
                "BTN",
                "CAD",
                "CHF",
                "CLP",
                "CNY")

        val currenciesString: String = TextUtils.join(",", listOfCurrencies)

        //The list of names for the Spinner when adding a card. Gotten from the extended currency
        // library
        fun getCurrenciesNames(): List<String>{
            val currencyNames: MutableList<String> = ArrayList<String>()

            val currencies = ExtendedCurrency.CURRENCIES
            var index = 0


            while (index < 24){
                if(currencies[index].code != "BMD" && currencies[index].code != "BZD" ) //These
                // are the currencies that there is no data for
                currencyNames.add(currencies[index].name)

                index++
            }

            currencyNames.add(0, "Nigeria Naira")

            return currencyNames
        }

    }


}