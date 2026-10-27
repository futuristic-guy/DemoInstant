package com.example.instantdemo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.instantdemo.models.PokemonPageDataHolder
import com.example.instantdemo.viewmodel.MainViewModel

@Composable
fun SuccessScreen(padding: PaddingValues, pokemonPageDataHolder: PokemonPageDataHolder, mainViewModel: MainViewModel, onElementClicked: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {



        if (pokemonPageDataHolder.pokemon == null &&
            pokemonPageDataHolder.pokemon.results.isEmpty()) {

            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Something Went Wrong", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(20.dp))

            }


        } else {

            val listState = rememberLazyListState()

            LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp), state = listState) {

                itemsIndexed(items = pokemonPageDataHolder.pokemon.results) { index,data ->

                    Text(text = data.name ?: "empty", color = Color.White, modifier = Modifier.clickable(enabled = true){
                        onElementClicked(index)
                    })

                }


            }

            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                    .collect { visibleItems ->
                        val visibleIndices = visibleItems.map { it.index }
                        // Run your logic for the currently visible indexes
                        visibleIndices.forEach { index ->
                            println("User is viewing index: $index")
                            if (index > (pokemonPageDataHolder.pokemon.results.size - 10)) {
                                println("passed " + (pokemonPageDataHolder.pokemon.results.size + 1))
                                mainViewModel.getPokemonData(limit = 40, offset = (pokemonPageDataHolder.pokemon.results.size + 1))
                            }
                        }
                    }
            }


        } // else ends



    }
}