package com.oluwatimilehin.cryptoconverter.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 04/12/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ConversionCalculator @Inject constructor(){

    fun calculateAmount(conversionMode: String, input: String, rate: BigDecimal): String{

        val formatter = DecimalFormat("#,###,###.###")

        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        var amount = BigDecimal.valueOf(numberFormat.parse(input).toDouble())

        when (conversionMode) {
            "from" -> {
                amount = amount.multiply(rate)
            }
            "to" -> {
                amount = amount.divide(rate, 3, RoundingMode.HALF_UP)
            }
        }

        return formatter.format(amount)

    }
}