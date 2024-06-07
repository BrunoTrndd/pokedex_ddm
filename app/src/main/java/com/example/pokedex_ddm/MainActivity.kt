package com.example.pokedex_ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex_android.ui.theme.JetpackComposePokedexTheme
import com.example.pokedex_ddm.databinding.ActivityMainBinding
import com.example.pokedex_ddm.ui.favorites.FavoritePokemonsScreen
import com.example.pokedex_ddm.ui.navigation.BottomNavigationBar
import com.example.pokedex_ddm.ui.navigation.MainViewModel
import com.example.pokedex_ddm.ui.pokemondetail.PokemonDetailFragment
import com.example.pokedex_ddm.ui.pokemonlist.PokemonListFragment
import com.example.pokedex_ddm.util.BottomNavItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePokedexTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController, viewModel = viewModel) }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavigationGraph(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController, startDestination = BottomNavItem.Home.screenRoute) {
            composable(BottomNavItem.Home.screenRoute) {
                PokemonListFragment(navController = navController)
            }
            composable("pokemon_detail_fragment/{dominantColor}/{pokemonName}",
                arguments = listOf(
                    navArgument("dominantColor") { type = NavType.IntType },
                    navArgument("pokemonName") { type = NavType.StringType }
                )
            ) {
                val dominantColor = remember {
                    val color = it.arguments?.getInt("dominantColor")
                    color?.let { Color(it) } ?: Color.White
                }
                val pokemonName = remember {
                    it.arguments?.getString("pokemonName")
                }
                PokemonDetailFragment(
                    dominantColor = dominantColor,
                    pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                    navController = navController)
            }
            composable(BottomNavItem.Favorites.screenRoute) {
                FavoritePokemonsScreen(navController = navController)
            }
            composable(BottomNavItem.Profile.screenRoute) {
//                SettingsScreen()
            }
        }
    }


}

