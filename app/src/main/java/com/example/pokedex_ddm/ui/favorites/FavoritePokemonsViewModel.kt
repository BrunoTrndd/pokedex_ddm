package com.example.pokedex_ddm.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_android.repository.PokemonRepository
import com.example.pokedex_ddm.data.models.PokedexListEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePokemonsViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _favoritePokemons = MutableStateFlow<List<PokedexListEntry>>(emptyList())
    val favoritePokemons: StateFlow<List<PokedexListEntry>> = _favoritePokemons

    init {
        loadFavoritePokemons()
    }

    private fun loadFavoritePokemons() {
        viewModelScope.launch {
            _favoritePokemons.value = repository.getFavoritePokemons()
        }
    }
}