package com.oluwatimilehin.cryptoconverter.presentation

import com.oluwatimilehin.cryptoconverter.conversion.ConversionContract
import com.oluwatimilehin.cryptoconverter.conversion.ConversionPresenter
import com.oluwatimilehin.cryptoconverter.utils.ConversionCalculator
import org.junit.Before
import org.mockito.Mock

/**
 * Created by Oluwatimilehin on 04/12/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ConversionPresenterTest{
    @Mock
    lateinit var view: ConversionContract.View

    @Mock
    lateinit var calculator: ConversionCalculator

    lateinit var presenter: ConversionPresenter

    @Before
    fun setUp(){
        presenter = ConversionPresenter(view, calculator)
    }
}