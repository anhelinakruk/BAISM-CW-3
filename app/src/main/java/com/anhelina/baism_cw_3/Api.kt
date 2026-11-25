package com.anhelina.baism_cw_3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val status: String
)

data class CharactersResponse(
    val results: List<Character>
)

// Definicja endpointów API
interface RickAndMortyApi {
    @GET("api/character")
    suspend fun getCharacters(): CharactersResponse

    @GET("api/character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character
}

// Klient Retrofit
object Api {
    val service: RickAndMortyApi = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RickAndMortyApi::class.java)
}