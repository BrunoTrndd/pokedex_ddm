package com.example.pokedex_ddm.ui.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.pokedex_android.repository.PokemonRepository
import com.example.pokedex_android.util.Resource
import com.example.pokedex_ddm.data.remote.responses.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String) : Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

}