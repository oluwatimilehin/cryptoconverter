package com.oluwatimilehin.cryptoconverter.conversion

/**
 * Created by Oluwatimilehin on 03/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface ConversionContract{

    interface View{
        fun updateFromField(amount: String)
        fun updateToField(amount: String)
    }

    interface Presenter{
        fun setAmount(rate: Double)
        fun convertAmount(input: String, conversionMode: String)
    }

}