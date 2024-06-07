package com.example.pokedex_ddm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex_ddm.data.models.FavoritePokemon

@Dao
interface PokemonDao {
    @Insert
    suspend fun insertFavorite(pokemon: FavoritePokemon)

    @Delete
    suspend fun deleteFavorite(pokemon: FavoritePokemon)

    @Query("SELECT * FROM favorite_pokemons")
    suspend fun getAllFavorites(): List<FavoritePokemon>
}