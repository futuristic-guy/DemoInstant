package com.example.instantdemo.models

data class Pokemon(
    var count : Int? = null,
    var next : String? = null,
    var previous: String? = null,
    var results: List<PokemonInfo> = emptyList()
)