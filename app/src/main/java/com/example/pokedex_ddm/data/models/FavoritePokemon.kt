package com.example.pokedex_ddm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemons")
data class FavoritePokemon(
    @PrimaryKey val name: String,
    val imageUrl: String,
    val number: Int
)