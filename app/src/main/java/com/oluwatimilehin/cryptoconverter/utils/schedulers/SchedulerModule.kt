package com.oluwatimilehin.cryptoconverter.utils.schedulers

import com.oluwatimilehin.cryptoconverter.data.di.RunOn
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Module
class SchedulerModule{

    @Provides
    @RunOn(SchedulerType.IO)
    fun provideIo() = Schedulers.io()

    @Provides
    @RunOn(SchedulerType.MAIN)
    fun provideMain() = AndroidSchedulers.mainThread()

    @Provides
    @RunOn(SchedulerType.SINGLE)
    fun provideSingle() = Schedulers.single()
}