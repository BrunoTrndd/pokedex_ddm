package com.example.pokedex_android.repository

import com.example.pokedex_android.util.Resource
import com.example.pokedex_ddm.data.PokeApi
import com.example.pokedex_ddm.data.dao.PokemonDao
import com.example.pokedex_ddm.data.models.FavoritePokemon
import com.example.pokedex_ddm.data.models.PokedexListEntry
import com.example.pokedex_ddm.data.remote.responses.Pokemon
import com.example.pokedex_ddm.data.remote.responses.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi,
    private val dao: PokemonDao
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "An unknown error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "An unknown error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun addPokemonToFavorites(entry: PokedexListEntry) {
        val favoritePokemon = FavoritePokemon(name = entry.pokemonName, imageUrl = entry.imageUrl, number = entry.number)
        dao.insertFavorite(favoritePokemon)
    }

    suspend fun removePokemonFromFavorites(entry: PokedexListEntry) {
        val favoritePokemon = FavoritePokemon(name = entry.pokemonName, imageUrl = entry.imageUrl, number = entry.number)
        dao.deleteFavorite(favoritePokemon)
    }

    suspend fun getFavoritePokemons(): List<PokedexListEntry> {
        return dao.getAllFavorites().map { entry ->
            PokedexListEntry(pokemonName = entry.name, imageUrl = entry.imageUrl, number = entry.number)
        }
    }
}