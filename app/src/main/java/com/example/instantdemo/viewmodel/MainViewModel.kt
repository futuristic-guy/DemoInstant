package com.example.instantdemo.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantdemo.data.remote.PokemonDataRepository
import com.example.instantdemo.models.PokemonPageDataHolder
import com.example.instantdemo.state.PokemonPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private var pokemonPageDataHolder : PokemonPageDataHolder = PokemonPageDataHolder.defaultValue

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pokemonDataRepository: PokemonDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonPageUiState>(PokemonPageUiState.Loading)
    val uiState : StateFlow<PokemonPageUiState> = _uiState.asStateFlow()

    init {
        getPokemonData(limit = pokemonPageDataHolder.limit, offset = pokemonPageDataHolder.offset)
    }


    fun getPokemonData(limit: Int, offset: Int, isPaginatedCall: Boolean = false) {

        if (isPaginatedCall) {
            pokemonPageDataHolder = pokemonPageDataHolder.copy(offset = offset)
        }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                pokemonDataRepository.getPokemonData(limit, offset).fold(
                    onSuccess = { data ->
                        println("Came here " + data.results.size)
                        println(data.toString())
                        pokemonPageDataHolder = pokemonPageDataHolder.copy(pokemon = data)
                        updateSuccessDataHolder(pokemonPageDataHolder)
                    },
                    onFailure = { exception ->
                        println("Came here failure")

                        _uiState.value = PokemonPageUiState.Failure(
                            message = exception.message ?: "Something Went Wrong"
                        )
                    }
                )

            }
        }

    }


    private fun updateSuccessDataHolder(pokemonPageDataHolder: PokemonPageDataHolder) {
        _uiState.value = PokemonPageUiState.Success(pokemonPageDataHolder)
    }



}