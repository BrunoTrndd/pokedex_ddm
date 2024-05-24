package com.example.pokedex_ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex_android.ui.theme.JetpackComposePokedexTheme
import com.example.pokedex_ddm.databinding.ActivityMainBinding
import com.example.pokedex_ddm.ui.pokemondetail.PokemonDetailFragment
import com.example.pokedex_ddm.ui.pokemonlist.PokemonListFragment
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
                NavHost(navController = navController, startDestination = "pokemon_list_fragment") {
                    composable("pokemon_list_fragment") {
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
                }
            }
        }
    }
}