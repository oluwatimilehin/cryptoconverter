package com.oluwatimilehin.cryptoconverter.conversion

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Oluwatimilehin on 03/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ConversionPresenter : ConversionContract.Presenter {
    override fun attachView(view: ConversionContract.View, rate: Double) {
        this.view = view
        this.rate = BigDecimal.valueOf(rate)
    }

    private lateinit var rate: BigDecimal
    lateinit var view: ConversionContract.View
    private val formatter = DecimalFormat("#,###,###.###")

    override fun convertAmount(input: String, conversion: String) {
        var amount: BigDecimal
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        amount = BigDecimal.valueOf(numberFormat.parse(input).toDouble())

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