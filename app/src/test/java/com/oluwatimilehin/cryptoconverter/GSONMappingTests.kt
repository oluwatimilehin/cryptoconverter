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
        val jsonString: String = "{\n" +
                "\"BTC\": {\n" +
                "\"EUR\": 4802.63,\n" +
                "\"USD\": 5659.39,\n" +
                "\"GBP\": 4341.1,\n" +
                "\"CZK\": 127962.62,\n" +
                "\"TRY\": 22003.54,\n" +
                "\"JPY\": 644689.25,\n" +
                "\"AED\": 21526.44,\n" +
                "\"AFN\": 17150,\n" +
                "\"ARS\": 96984.22,\n" +
                "\"AUD\": 7221.53,\n" +
                "\"BDT\": 567627.74,\n" +
                "\"BGN\": 9211,\n" +
                "\"BHD\": 2240,\n" +
                "\"BND\": 4115.47,\n" +
                "\"BOB\": 362318.84,\n" +
                "\"BRL\": 17101,\n" +
                "\"BTN\": 165555.56,\n" +
                "\"CAD\": 7138.4,\n" +
                "\"CHF\": 5605.04,\n" +
                "\"CLP\": 3728998.14,\n" +
                "\"CNY\": 35612.29,\n" +
                "\"COP\": 14667287.75\n" +
                "},\n" +
                "\"ETH\": {\n" +
                "\"EUR\": 259.98,\n" +
                "\"USD\": 305.41,\n" +
                "\"GBP\": 234.85,\n" +
                "\"CZK\": 6922.78,\n" +
                "\"TRY\": 1190.39,\n" +
                "\"JPY\": 34672.53,\n" +
                "\"AED\": 1164.58,\n" +
                "\"AFN\": 927.82,\n" +
                "\"ARS\": 5246.85,\n" +
                "\"AUD\": 396.3,\n" +
                "\"BDT\": 30708.66,\n" +
                "\"BGN\": 498.32,\n" +
                "\"BHD\": 121.18,\n" +
                "\"BND\": 222.65,\n" +
                "\"BOB\": 19601.45,\n" +
                "\"BRL\": 925.16,\n" +
                "\"BTN\": 10101.01,\n" +
                "\"CAD\": 384.14,\n" +
                "\"CHF\": 303.23,\n" +
                "\"CLP\": 201738.8,\n" +
                "\"CNY\": 1969.22,\n" +
                "\"COP\": 793500.27\n" +
                "}\n" +
                "}";

              val gson: Gson = Gson()
              val exchange: ExchangeRate = gson.fromJson(jsonString, ExchangeRate::class.java)

              assertTrue(exchange.ethRates.size == 22);
              assertTrue(exchange.btcRates.size == 22);

    }
}