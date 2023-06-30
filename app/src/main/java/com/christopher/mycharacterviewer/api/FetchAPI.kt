package com.christopher.mycharacterviewer.api

import com.christopher.mycharacterviewer.model.CharactersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FetchAPI {

    @GET(".")
    suspend fun getCharacters(
        @Query("q") characterType: String,
        @Query("format") format: String = "json"
    ) : Response<CharactersModel>
}