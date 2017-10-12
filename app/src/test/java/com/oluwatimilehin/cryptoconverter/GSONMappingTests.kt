package com.oluwatimilehin.cryptoconverter

import com.google.gson.Gson
import com.oluwatimilehin.cryptoconverter.data.ExchangeRate
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class GSONMappingTests {
    @Test
    fun gsonMapping(){
        val jsonString : String = "{\n" +
                "\"ETH\": {\n" +
                "\"USD\": 1.13,\n" +
                "\"EUR\": 1.04,\n" +
                "\"NGN\": 290.85,\n" +
                "\"CAD\": 1.63,\n" +
                "\"AUD\": 1.6,\n" +
                "\"JPY\": 133.43\n" +
                "}\n" +
                "}";

        val gson: Gson = Gson()
        val eth: ExchangeRate? = gson.fromJson(jsonString, ExchangeRate::class.java)

        assertTrue(eth?.ethRates?.size == 6);
    }
}