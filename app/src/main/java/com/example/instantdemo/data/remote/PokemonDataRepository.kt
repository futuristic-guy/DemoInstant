package com.example.instantdemo.data.remote

import com.example.instantdemo.models.Pokemon

interface PokemonDataRepository {

    suspend fun getPokemonData(limit: Int, offset: Int) : Result<Pokemon>

}