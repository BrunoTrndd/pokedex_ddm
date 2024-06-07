package com.example.pokedex_ddm.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokedex_ddm.ui.pokemonlist.PokedexEntry

@Composable
fun FavoritePokemonsScreen(
    navController: NavController,
    viewModel: FavoritePokemonsViewModel = hiltViewModel()
) {
    val favoritePokemons by viewModel.favoritePokemons.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(favoritePokemons) { pokemon ->
            PokedexEntry(
                entry = pokemon,
                navController = navController
            )
        }
    }
}