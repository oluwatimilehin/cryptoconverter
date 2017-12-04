package com.oluwatimilehin.cryptoconverter.conversion

import com.oluwatimilehin.cryptoconverter.utils.ConversionCalculator
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 03/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ConversionPresenter @Inject constructor(val view: ConversionContract.View,val calculator:
ConversionCalculator) :
        ConversionContract.Presenter  {

    override fun setAmount(rate: Double) {
        this.rate = BigDecimal.valueOf(rate)
    }

    private lateinit var rate: BigDecimal

    override fun convertAmount(input: String, conversionMode: String) {
        val newAmount = calculator.calculateAmount(conversionMode, input, rate)

        when(conversionMode){
            "from" -> view.updateToField(newAmount)
            "to" -> view.updateFromField(newAmount)
        }
    }

}