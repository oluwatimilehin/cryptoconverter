package com.oluwatimilehin.cryptoconverter.presentation

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.oluwatimilehin.cryptoconverter.conversion.ConversionContract
import com.oluwatimilehin.cryptoconverter.conversion.ConversionPresenter
import com.oluwatimilehin.cryptoconverter.utils.ConversionCalculator
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

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

        MockitoAnnotations.initMocks(this)
        presenter = ConversionPresenter(view, calculator)
    }

    @Test
    fun typingAnInput_shouldUpdateTheRightField(){
        //Given
        whenever(calculator.calculateAmount(any(), any(), any())).thenReturn("11112223")

        //When conversion mode is "from"
        presenter.setAmount(2333.8)
        presenter.convertAmount("64","from")

        //Then the to field should be updated
        verify(view).updateToField(any())

        //When conversion mode is "to"
        presenter.convertAmount("64","to")

        //Then the to field should be updated
        verify(view).updateFromField(any())

    }
}