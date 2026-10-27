package com.example.instantdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instantdemo.state.PokemonDetailPageUiState
import com.example.instantdemo.state.PokemonPageUiState
import com.example.instantdemo.ui.theme.InstantDemoTheme
import com.example.instantdemo.view.FailureScreen
import com.example.instantdemo.view.LoadingScreen
import com.example.instantdemo.view.PokemonDetailPage
import com.example.instantdemo.view.SuccessScreen
import com.example.instantdemo.viewmodel.MainViewModel
import com.example.instantdemo.viewmodel.PokemonDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstantDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                    val navController = rememberNavController()

                    var currentElementName : String = ""

                    NavHost(navController = navController, startDestination = "PokemonFirstPage") {
                        composable(route = "PokemonFirstPage") {
                            val mainViewModel: MainViewModel = hiltViewModel<MainViewModel>()

                            val state = mainViewModel.uiState.collectAsState()

                            when (val uiState = state.value) {

                                is PokemonPageUiState.Loading -> {
                                    LoadingScreen()
                                }

                                is PokemonPageUiState.Success -> {

                                    SuccessScreen(
                                        padding = innerPadding,
                                        pokemonPageDataHolder = uiState.pokemonPageDataHolder,
                                        mainViewModel = mainViewModel,
                                        onElementClicked = { index ->

                                            currentElementName = uiState.pokemonPageDataHolder.pokemon.results.get(index).name.toString()
                                            navController.navigate("PokemonDetailPage")

                                        })

                                }

                                is PokemonPageUiState.Failure -> {
                                    // need to be changed
                                    FailureScreen(onRetryClick = {
                                        mainViewModel.getPokemonData(
                                            limit = 10,
                                            offset = 0
                                        )
                                    })
                                }

                            }
                        }
                        composable(route = "PokemonDetailPage") {



                            val pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel<PokemonDetailViewModel>()

                            val state = pokemonDetailViewModel.uiState.collectAsState()

                            PokemonDetailPage(pathName = currentElementName, pokemonDetailViewModel = pokemonDetailViewModel)

                            when (val uiState = state.value) {

                                is PokemonDetailPageUiState.Loading -> {
                                    LoadingScreen()
                                }

                                is PokemonDetailPageUiState.Success -> {

                                    Column(modifier = Modifier.fillMaxSize().padding(20.dp),
                                        verticalArrangement = Arrangement.Center,
                                    ) {

                                        Text("Detail Page", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text("Name : " + uiState.pokemonDetailPageDataHolder.pokemonData.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text("Weight : " + uiState.pokemonDetailPageDataHolder.pokemonData.weight, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)


                                    }

                                }

                                is PokemonDetailPageUiState.Failure -> {
                                    // need to be changed
                                    FailureScreen(onRetryClick = {
                                        pokemonDetailViewModel.getPokemonDetailData(pathName = currentElementName)
                                    })
                                }

                            }



                        }
                    }







                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    InstantDemoTheme {
//        Greeting("Android")
//    }
//}