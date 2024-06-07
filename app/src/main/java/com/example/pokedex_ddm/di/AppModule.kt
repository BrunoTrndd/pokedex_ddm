package com.example.pokedex_ddm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pokedex_android.repository.PokemonRepository
import com.example.pokedex_android.util.Constants
import com.example.pokedex_ddm.data.PokeApi
import com.example.pokedex_ddm.data.PokemonDatabase
import com.example.pokedex_ddm.data.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi,
        dao: PokemonDao
    ) = PokemonRepository(api, dao)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): PokemonDatabase {
        return Room.databaseBuilder(app, PokemonDatabase::class.java, "pokedex_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDatabase): PokemonDao {
        return db.pokemonDao()
    }
}