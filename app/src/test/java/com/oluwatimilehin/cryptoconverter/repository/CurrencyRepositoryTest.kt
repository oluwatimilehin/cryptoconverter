package com.oluwatimilehin.cryptoconverter.repository

import com.nhaarman.mockito_kotlin.whenever
import com.oluwatimilehin.cryptoconverter.data.local.LocalCurrencyDataManager
import com.oluwatimilehin.cryptoconverter.data.models.Currency
import com.oluwatimilehin.cryptoconverter.data.remote.RemoteCurrencyDataSource
import com.oluwatimilehin.cryptoconverter.data.repositories.CurrencyRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Oluwatimilehin on 27/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

class CurrencyRepositoryTest{

    lateinit var remoteCurrencyDataSource: RemoteCurrencyDataSource
    lateinit var localCurrencyDataManager: LocalCurrencyDataManager
    lateinit var currencyRepository: CurrencyRepository

    @Before
    fun setUp(){
        //MockitoAnnotations.initMocks(this)
        localCurrencyDataManager = Mockito.mock(LocalCurrencyDataManager::class.java)
        remoteCurrencyDataSource = Mockito.mock(RemoteCurrencyDataSource::class.java)

        currencyRepository = CurrencyRepository(remoteCurrencyDataSource, localCurrencyDataManager)
    }

    @Test
    fun checkIfCurrenciesExist_shouldTriggerError_whenEmpty(){
        val list = emptyList<Currency>()
        whenever(localCurrencyDataManager.getCurrenciesAsSingle()).thenReturn(Single.just(list))

        currencyRepository.checkIfCurrenciesExist()
                .test()
                .assertError(NoSuchElementException::class.java)
    }
}