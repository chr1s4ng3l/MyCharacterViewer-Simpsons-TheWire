package com.christopher.mycharacterviewer.repository

import com.christopher.mycharacterviewer.model.CharactersModel
import com.christopher.mycharacterviewer.api.FetchAPI
import com.christopher.mycharacterviewer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val fetchAPI: FetchAPI
) :
    Repository {

    override fun getCharacters(characterType: String): Flow<Resource<CharactersModel>> {
        return flow {
            try {
                val response = fetchAPI.getCharacters(characterType)
                if (response.isSuccessful) {
                    val tvShow = response.body() as CharactersModel
                    emit(Resource.Success(tvShow))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }
}