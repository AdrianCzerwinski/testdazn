package pl.adrianczerwinski.common.datetime

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DateTimeConverterModule {

    @Binds
    fun dateTimeConverter(impl: DateTimeConverterImpl): DateTimeConverter

    @Binds
    fun clock(impl: ClockImpl): Clock
}
