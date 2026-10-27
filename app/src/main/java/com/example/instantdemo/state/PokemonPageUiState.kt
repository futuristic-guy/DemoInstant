package com.example.instantdemo.state

import com.example.instantdemo.models.PokemonPageDataHolder

sealed class PokemonPageUiState {

    data object Loading: PokemonPageUiState()

    data class Success(val pokemonPageDataHolder: PokemonPageDataHolder) : PokemonPageUiState()

    data class Failure(val message: String) : PokemonPageUiState()


}