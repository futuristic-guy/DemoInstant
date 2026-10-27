package com.example.instantdemo.models

data class PokemonDetailPageDataHolder(
    var pokemonData: PokemonDetailData = PokemonDetailData(),
) {
    companion object {
        val defaultValue = PokemonDetailPageDataHolder()
    }
}
