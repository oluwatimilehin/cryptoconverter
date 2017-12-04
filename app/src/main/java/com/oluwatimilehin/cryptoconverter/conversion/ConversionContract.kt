package com.oluwatimilehin.cryptoconverter.conversion

/**
 * Created by Oluwatimilehin on 03/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface ConversionContract{

    interface View{
        fun showAmount(amount: String, conversion: String)
    }

    interface Presenter{
        fun setAmount(rate: Double)
        fun convertAmount(input: String, conversion: String)
    }

}