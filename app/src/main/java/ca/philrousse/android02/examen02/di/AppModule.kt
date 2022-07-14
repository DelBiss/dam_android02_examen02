package ca.philrousse.android02.examen02.di

import ca.philrousse.android02.examen02.data.remote.ClientApi
import ca.philrousse.android02.examen02.data.remote.ClientApiImpl
import ca.philrousse.android02.examen02.data.remote.dto.ClientInfoDocument
import ca.philrousse.android02.examen02.data.remote.dto.QuerySnapshotParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClientApi(clientInfoParser: QuerySnapshotParser<ClientInfoDocument>):ClientApi{
        return ClientApiImpl(clientInfoParser)
    }
}