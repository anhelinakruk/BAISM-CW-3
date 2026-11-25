package com.anhelina.baism_cw_3

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
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
        // długi timeout, żeby zdążyć edytować request zatrzymany w mitmproxy
        .client(OkHttpClient.Builder().readTimeout(2, TimeUnit.MINUTES).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RickAndMortyApi::class.java)
}