package com.oluwatimilehin.cryptoconverter.currencyconversion

/**
 * Created by Oluwatimilehin on 03/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface ConversionContract{

    interface View{
        fun showAmount(amount: String, conversion: String)
    }

    interface Presenter{
        fun convertAmount(input: String, conversion: String)
        fun attachView(view: ConversionContract.View, rate: Double)
    }

}