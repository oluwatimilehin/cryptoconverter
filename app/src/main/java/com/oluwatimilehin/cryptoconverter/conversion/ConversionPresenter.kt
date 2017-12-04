package com.oluwatimilehin.cryptoconverter.conversion

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 03/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ConversionPresenter @Inject constructor(val view: ConversionContract.View) :
        ConversionContract.Presenter  {
    override fun setAmount(rate: Double) {
        this.rate = BigDecimal.valueOf(rate)
    }

    private lateinit var rate: BigDecimal
    private val formatter = DecimalFormat("#,###,###.###")

    override fun convertAmount(input: String, conversion: String) {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        var amount = BigDecimal.valueOf(numberFormat.parse(input).toDouble())

        when (conversion) {
            "from" -> {
                amount = amount.multiply(rate)
            }
            "to" -> {
                amount = amount.divide(rate, 3, RoundingMode.HALF_UP)
            }
        }

        val newAmount = formatter.format(amount)
        view.showAmount(newAmount, conversion)
    }

}