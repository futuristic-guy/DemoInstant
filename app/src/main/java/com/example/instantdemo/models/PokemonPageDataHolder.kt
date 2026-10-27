package com.example.instantdemo.models

data class PokemonPageDataHolder(
    var pokemon: Pokemon = Pokemon(),
    var limit: Int = 40,
    var offset: Int = 0,
) {
    companion object {
        val defaultValue = PokemonPageDataHolder()
    }
}
