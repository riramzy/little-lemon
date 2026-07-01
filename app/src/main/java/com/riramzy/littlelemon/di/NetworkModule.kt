package com.riramzy.littlelemon.di

import com.riramzy.littlelemon.data.remote.MenuApiService
import com.riramzy.littlelemon.data.remote.MenuApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindMenuApiService(impl: MenuApiServiceImpl): MenuApiService

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient {
            return HttpClient(Android) {
                install(ContentNegotiation) {
                    val jsonConfig = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                    json(jsonConfig)
                    register(
                        io.ktor.http.ContentType.Text.Plain,
                        io.ktor.serialization.kotlinx.KotlinxSerializationConverter(jsonConfig)
                    )
                }
            }
        }
    }
}