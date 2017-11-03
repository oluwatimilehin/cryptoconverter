package com.oluwatimilehin.cryptoconverter.currencyconversion

import java.math.BigDecimal
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

    private lateinit var rate: BigDecimal;
    lateinit var view: ConversionContract.View
    private val formatter = DecimalFormat("#,###,###.###")

    override fun convertAmount(input: String, conversion: String) {
        var amount: BigDecimal
        amount = try {
            BigDecimal(input)
        } catch (e: NumberFormatException) {
            val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
            BigDecimal(numberFormat.parse(input).toString())
        }

        when (conversion) {
            "from" -> {
                amount *= rate
            }
            "to" -> {
                amount /= rate
            }
        }

        val newAmount = formatter.format(amount)
        view.showAmount(newAmount, conversion)
    }

}