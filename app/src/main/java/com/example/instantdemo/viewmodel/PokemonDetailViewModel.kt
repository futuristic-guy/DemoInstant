package com.example.instantdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantdemo.data.remote.PokemonDetailDataRepository
import com.example.instantdemo.models.PokemonDetailPageDataHolder
import com.example.instantdemo.models.PokemonPageDataHolder
import com.example.instantdemo.state.PokemonDetailPageUiState
import com.example.instantdemo.state.PokemonPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private var pokemonDetailPageDataHolder : PokemonDetailPageDataHolder = PokemonDetailPageDataHolder.defaultValue

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonDetailDataRepository: PokemonDetailDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailPageUiState>(PokemonDetailPageUiState.Loading)
    val uiState : StateFlow<PokemonDetailPageUiState> = _uiState.asStateFlow()


    fun getPokemonDetailData(pathName: String) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                pokemonDetailDataRepository.getPokemonDetailData(name = pathName).fold(
                    onSuccess = { data ->

                        pokemonDetailPageDataHolder = pokemonDetailPageDataHolder.copy(pokemonData = data)
                        updateSuccessDataHolder(pokemonDetailPageDataHolder)
                    },
                    onFailure = { exception ->
                        _uiState.value = PokemonDetailPageUiState.Failure(
                            message = exception.message ?: "Something Went Wrong"
                        )

                    }
                )

            }
        }

    }

    private fun updateSuccessDataHolder(pokemonDetailPageDataHolder: PokemonDetailPageDataHolder) {
        _uiState.value = PokemonDetailPageUiState.Success(pokemonDetailPageDataHolder)
    }


}