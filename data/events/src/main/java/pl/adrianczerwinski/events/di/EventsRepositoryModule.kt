package pl.adrianczerwinski.events.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.adrianczerwinski.events.EventsRepository
import pl.adrianczerwinski.events.EventsRepositoryImpl

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
internal abstract class EventsRepositoryModule {

    @Binds
    abstract fun eventsRepository(impl: EventsRepositoryImpl): EventsRepository
}
