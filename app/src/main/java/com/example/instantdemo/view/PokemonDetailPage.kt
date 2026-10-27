package com.example.instantdemo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instantdemo.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailPage(pokemonDetailViewModel: PokemonDetailViewModel, pathName: String) {

    LaunchedEffect(Unit) {
        pokemonDetailViewModel.getPokemonDetailData(pathName)
    }



}