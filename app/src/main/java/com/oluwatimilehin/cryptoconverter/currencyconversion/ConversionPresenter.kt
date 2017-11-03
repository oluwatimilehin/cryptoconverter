package com.oluwatimilehin.cryptoconverter.currencyconversion

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
        this.rate = rate
    }

    var rate: Double = 0.0
    lateinit var view: ConversionContract.View
    private val formatter = DecimalFormat("#,###,###")

    override fun convertAmount(input: String, conversion: String) {
        var amount: Double
        amount = try {
            input.toDouble()
        } catch (e: NumberFormatException) {
            val numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
            numberFormat.parse(input).toDouble()
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