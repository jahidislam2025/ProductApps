package com.qsoft.network.di.qualifier

import com.qsoft.network.di.TypeEnum
import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicNetwork(val value: TypeEnum)
