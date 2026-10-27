package com.example.instantdemo.data.network

import com.example.instantdemo.models.Pokemon
import com.example.instantdemo.models.PokemonDetailData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonDataSource {

    @GET("pokemon")
    suspend fun getPokemonData(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ) : Pokemon


    @GET("pokemon/{name}")
    suspend fun getPokemonDetailData(
        @Path("name") name: String,
    ) : PokemonDetailData
}