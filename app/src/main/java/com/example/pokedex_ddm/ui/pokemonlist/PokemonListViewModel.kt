package com.example.pokedex_ddm.ui.pokemonlist;

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex_android.repository.PokemonRepository;
import com.example.pokedex_android.util.Constants
import com.example.pokedex_android.util.Resource
import com.example.pokedex_ddm.data.models.PokedexListEntry
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

@HiltViewModel
class PokemonListViewModel @Inject constructor (
    private val repository : PokemonRepository
) : ViewModel() {

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private val _favoritePokemons = MutableStateFlow<List<PokedexListEntry>>(emptyList())
    val favoritePokemons: StateFlow<List<PokedexListEntry>> = _favoritePokemons

    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if(isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                    it.number.toString() == query.trim()
            }
            if(isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value = true
        }

    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            val result = repository.getPokemonList(Constants.PAGE_SIZE, curPage * Constants.PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    endReached.value = curPage * Constants.PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
//                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${number}.gif"
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

            is Resource.Loading -> { }
            }
        }
    }


    fun calcDominantColor(drawable: Drawable, onFinish:(Color)-> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun addToFavorites(entry: PokedexListEntry) {
        viewModelScope.launch {
            repository.addPokemonToFavorites(entry)
            _favoritePokemons.value = repository.getFavoritePokemons()
        }
    }

    fun isPokemonFavorite(pokemonName: String): StateFlow<Boolean> {
        val isFavorite = MutableStateFlow(false)
        viewModelScope.launch {
            val favorites = repository.getFavoritePokemons()
            isFavorite.value = favorites.any { it.pokemonName == pokemonName }
        }
        return isFavorite
    }

    fun removeFromFavorites(entry: PokedexListEntry) {
        viewModelScope.launch {
            repository.removePokemonFromFavorites(entry)
            _favoritePokemons.value = repository.getFavoritePokemons()
        }
    }

}
