package com.oluwatimilehin.cryptoconverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
                "\"USD\": 306.96,\n" +
                "\"EUR\": 257.05,\n" +
                "\"NGN\": 107670.08,\n" +
                "\"CAD\": 385.06,\n" +
                "\"AUD\": 394.85,\n" +
                "\"JPY\": 34692.37,\n" +
                "\"TRY\": 1212.31\n" +
                "}"

        val type = object : TypeToken<HashMap<String, String>>(){}.type;
        val map: HashMap<String, String> = Gson().fromJson(jsonString, type);

        assertTrue(map.size == 7);
    }
}