package com.christopher.mycharacterviewer.repository

import com.christopher.mycharacterviewer.model.CharactersModel
import com.christopher.mycharacterviewer.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCharacters(characterType: String) : Flow<Resource<CharactersModel>>
}