package com.oluwatimilehin.cryptoconverter.presentation

import android.database.sqlite.SQLiteConstraintException
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.oluwatimilehin.cryptoconverter.addcard.AddCardContract
import com.oluwatimilehin.cryptoconverter.addcard.AddCardPresenter
import com.oluwatimilehin.cryptoconverter.utils.ExtendedCurrencyWrapper
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

/**
 * Created by Oluwatimilehin on 04/12/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class AddCardPresenterTest: BasePresenterTest(){

    @Mock
    lateinit var view: AddCardContract.View

    @Mock
    lateinit var extendedCurrencyWrapper: ExtendedCurrencyWrapper

    lateinit var addCardPresenter: AddCardPresenter

    @Before
    override fun setUp() {
        super.setUp()

        addCardPresenter = AddCardPresenter(currencyRepository, cardsRepository, view, ioThread,
                mainThread, extendedCurrencyWrapper)
    }

    @Test
    fun savingCard_thatAlreadyExists_shouldShowError(){

        //Given
        whenever(cardsRepository.saveCard(any())).thenAnswer { throw SQLiteConstraintException()}
        `when`(currencyRepository.getAmountValue(any(), any())).thenReturn(Single
                .just(7000.0))
        whenever(extendedCurrencyWrapper.getCurrencyByName(any())).thenReturn(ExtendedCurrency
        ("NGN", "Naira", "XXX", 2233))

        //When
         addCardPresenter.saveCard(cardOne.from, cardOne.to)
         scheduler.triggerActions()

        //Then
        verify(view).cardExistsError()

    }

    @Test
    fun savingNewCard_shouldShowSuccessMessage(){

        //Given
        whenever(cardsRepository.saveCard(any())).thenAnswer { Any() }
        `when`(currencyRepository.getAmountValue(any(), any())).thenReturn(Single
                .just(7000.0))
        whenever(extendedCurrencyWrapper.getCurrencyByName(any())).thenReturn(ExtendedCurrency
        ("NGN", "Naira", "XXX", 2233))

        //When
        addCardPresenter.saveCard(cardOne.from,cardOne.to)
        scheduler.triggerActions()

        //Then
        verify(view).saveCardSuccess()
    }
}