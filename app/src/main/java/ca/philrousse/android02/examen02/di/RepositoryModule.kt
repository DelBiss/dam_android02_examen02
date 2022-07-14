package ca.philrousse.android02.examen02.di

import ca.philrousse.android02.examen02.data.remote.dto.ClientInfoDocument
import ca.philrousse.android02.examen02.data.remote.dto.ClientListParser
import ca.philrousse.android02.examen02.data.remote.dto.QuerySnapshotParser
import ca.philrousse.android02.examen02.data.repository.ClientRepositoryImpl
import ca.philrousse.android02.examen02.domain.repository.ClientRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindClientListParser(
        clientListParser: ClientListParser
    ): QuerySnapshotParser<ClientInfoDocument>

    @Binds
    @Singleton
    abstract fun bindClientRepository(
        clientRepositoryImpl: ClientRepositoryImpl
    ): ClientRepository
}