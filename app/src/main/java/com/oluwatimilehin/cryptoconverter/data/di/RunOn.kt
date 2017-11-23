package com.oluwatimilehin.cryptoconverter.data.di

import com.oluwatimilehin.cryptoconverter.utils.schedulers.SchedulerType
import javax.inject.Qualifier

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RunOn(val type: SchedulerType = SchedulerType.IO)