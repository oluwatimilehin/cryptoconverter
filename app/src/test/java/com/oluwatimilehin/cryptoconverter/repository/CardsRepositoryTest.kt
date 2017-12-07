package com.oluwatimilehin.cryptoconverter.repository

import com.nhaarman.mockito_kotlin.whenever
import com.oluwatimilehin.cryptoconverter.data.di.Local
import com.oluwatimilehin.cryptoconverter.data.local.CardsDataManager
import com.oluwatimilehin.cryptoconverter.data.models.Card
import com.oluwatimilehin.cryptoconverter.data.repositories.CardsRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Oluwatimilehin on 27/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsRepositoryTest{

    @Mock @Local
    lateinit var localCardsDataManager: CardsDataManager

    lateinit var cardsRepository: CardsRepository

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        cardsRepository = CardsRepository(localCardsDataManager)
    }

    @Test
    fun getAllCards_shouldTriggerError_whenEmpty(){
        val list = emptyList<Card>()

        //given
        whenever(localCardsDataManager.getCards()).thenReturn(Single.just(list))

        //when
        val testObserver = TestObserver.create<List<Card>>()
        cardsRepository.getAllCards()
                .subscribe(testObserver)

        //then
        testObserver.assertError(NoSuchElementException::class.java)

    }
}