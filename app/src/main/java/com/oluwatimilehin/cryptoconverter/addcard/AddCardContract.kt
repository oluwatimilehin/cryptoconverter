package com.oluwatimilehin.cryptoconverter.addcard

/**
 * Created by Oluwatimilehin on 30/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface AddCardContract{
    interface View{
        fun cardExistsError()
        fun saveCardSuccess()
    }

    interface Presenter{
        fun saveCard(from: String, to: String)
        fun attachView(view: AddCardContract.View)
        fun clearDisposables()
    }
}