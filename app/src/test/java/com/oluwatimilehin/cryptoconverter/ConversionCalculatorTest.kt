package com.oluwatimilehin.cryptoconverter

import com.oluwatimilehin.cryptoconverter.utils.ConversionCalculator
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by Oluwatimilehin on 07/12/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ConversionCalculatorTest{

    @Test
    fun calculate(){
        val calculator = ConversionCalculator()

        assertEquals(BigInteger.valueOf(400), calculator.calculateAmount("from", "20",
                BigDecimal.valueOf(20.00)).toBigInteger())

        assertEquals(BigInteger.valueOf(20), calculator.calculateAmount("to", "400",
                BigDecimal.valueOf(20.00)).toBigInteger())

    }
}