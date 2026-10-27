package com.example.instantdemo.state

import com.example.instantdemo.models.PokemonDetailPageDataHolder
import com.example.instantdemo.models.PokemonPageDataHolder

sealed class PokemonDetailPageUiState {

    data object Loading: PokemonDetailPageUiState()

    data class Success(val pokemonDetailPageDataHolder: PokemonDetailPageDataHolder) : PokemonDetailPageUiState()

    data class Failure(val message: String) : PokemonDetailPageUiState()


}