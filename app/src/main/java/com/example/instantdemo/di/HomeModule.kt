package com.example.instantdemo.di

import com.example.instantdemo.data.network.PokemonDataSource
import com.example.instantdemo.data.remote.PokemonDataRepository
import com.example.instantdemo.data.remote.PokemonDataSourceRepositoryImpl
import com.example.instantdemo.data.remote.PokemonDetailDataRepository
import com.example.instantdemo.data.remote.PokemonDetailDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDataSource(
        retrofit: Retrofit
    ) : PokemonDataSource {
        return retrofit.create(PokemonDataSource::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonDataRepository(pokemonDataSource: PokemonDataSource) : PokemonDataRepository {
        return PokemonDataSourceRepositoryImpl(pokemonDataSource)
    }

    @Provides
    @Singleton
    fun providePokemonDetailDataRepository(pokemonDataSource: PokemonDataSource) : PokemonDetailDataRepository {
        return PokemonDetailDataRepositoryImpl(pokemonDataSource)
    }


}