package com.oluwatimilehin.cryptoconverter.presentation

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.oluwatimilehin.cryptoconverter.cards.CardsContract
import com.oluwatimilehin.cryptoconverter.cards.CardsPresenter
import com.oluwatimilehin.cryptoconverter.data.models.Card
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.net.SocketTimeoutException


/**
 * Created by Oluwatimilehin on 28/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

class CardsPresenterTest : BasePresenterTest() {

    @Mock
    lateinit var view: CardsContract.View

    lateinit var cardsPresenter: CardsPresenter

    @Before
    override fun setUp() {
        super.setUp()

        cardsPresenter = CardsPresenter(cardsRepository, currencyRepository, view, ioThread, mainThread)
    }

    @Test
    fun checkIfCurrenciesExist_shouldRestrictUI_whenNoCurrencyExists() {

        //Given
        whenever(currencyRepository.checkIfCurrenciesExist()).thenReturn(Single.error(NoSuchElementException()))

        //When
        cardsPresenter.checkIfCurrenciesExist()
        scheduler.triggerActions()

        //Then
        verify(view).showEmptyCurrenciesError()
        verify(view, never()).currenciesExist()
    }

    @Test
    fun checkIfCurrenciesExist_shouldEnableUI_whenCurrenciesExist() {

        //Given
        whenever(currencyRepository.checkIfCurrenciesExist()).thenReturn(Single.just(listOf()))

        //When
        cardsPresenter.checkIfCurrenciesExist()
        scheduler.triggerActions()

        //Then
        verify(view).currenciesExist()
        verify(view, never()).showEmptyCurrenciesError()
    }

    @Test
    fun loadCards_showShowEmptyView_whenNoCardsExist() {

        //Given
        whenever(cardsRepository.getAllCards()).thenReturn(Single.error(NoSuchElementException()))

        //When
        cardsPresenter.loadCards()
        scheduler.triggerActions()

        //Then
        verify(view).showEmptyCardsError()
    }

    @Test
    fun loadCards_showCardsList_whenCardsExist() {
        val cardOne = Card(0, "Naira", "BTC", "ETH", 2000.3, 1)
        val cardTwo = Card(0, "Naira", "BTC", "ETH", 2000.3, 1)

        //Given
        whenever(cardsRepository.getAllCards()).thenReturn(Single.just(listOf(cardOne, cardTwo)))

        //When
        cardsPresenter.loadCards()
        scheduler.triggerActions()

        //Then
        verify(view).updateRecyclerView(listOf(cardOne, cardTwo))
        verify(view).cardsExist()
    }

    @Test
    fun deleteAllCards_shouldShowEmptyViewAfter() {

        val repositoryDeleteObserver = TestObserver.create<Unit>()
        val deleteCompletable = Completable.complete().subscribe(repositoryDeleteObserver)

        //Given
        given(cardsRepository.deleteAll()).willAnswer({ deleteCompletable })
        `when`(cardsRepository.getAllCards()).thenReturn(Single.error(NoSuchElementException()))

        //When
        cardsPresenter.deleteAllCards()
        scheduler.triggerActions()

        //Then
        verify(view).showEmptyCardsError()
    }

    @Test
    fun deleteCard_shouldShowMessage_whenCardIsDeleted(){
        val repositoryDeleteObserver = TestObserver.create<Unit>()
        val deleteCompletable = Completable.complete().subscribe(repositoryDeleteObserver)

        //Given
        given(cardsRepository.deleteCard(cardOne)).willAnswer{ deleteCompletable }
        `when`(cardsRepository.getAllCards()).thenReturn(Single.just(listOf(cardOne,cardTwo)))

        //When
        cardsPresenter.deleteCard(cardOne)
        scheduler.triggerActions()

        //Then
        verify(view).showCardDeleted()
    }


    @Test
    fun loadCurrencies_shouldShowErrorMessage_whenNetworkErrorHappens() {

        //Given
        whenever(currencyRepository.loadCurrencies()).thenReturn(Single.error
        (SocketTimeoutException()))

        //When
        cardsPresenter.loadCurrencies(true)
        scheduler.triggerActions()

        //Then
        verify(view).showApiCallError()
    }

    @Test
    fun loadCurrencies_shouldUpdateUI_whenCurrenciesExist() {

        //Given
        whenever(currencyRepository.loadCurrencies()).thenReturn(Single.just(mutableListOf
        (currencyOne, currencyTwo)))

        //When
        cardsPresenter.loadCurrencies(true)
        scheduler.triggerActions()

        //Then
        verify(view).currenciesExist()
        verify(view).onDatabaseUpdateSuccess()
    }

    @Test
    fun navigateToConversionScreen(){

        //When
        cardsPresenter.navigateToConversionScreen(cardOne)

        //Then
        verify(view).showConversionScreen(cardOne.from, cardOne.to, cardOne.amount)
    }

    @Test
    fun navigateToAddCardScreen(){

        //When
        cardsPresenter.navigateToAddCardScreen()

        //Then
        verify(view).showAddCard()
    }

}