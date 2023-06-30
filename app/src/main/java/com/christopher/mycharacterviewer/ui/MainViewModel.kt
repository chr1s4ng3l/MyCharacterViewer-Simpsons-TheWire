package com.christopher.mycharacterviewer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christopher.mycharacterviewer.model.RelatedTopicModel
import com.christopher.mycharacterviewer.repository.Repository
import com.christopher.mycharacterviewer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _characters: MutableStateFlow<Resource<List<RelatedTopicModel>>> = MutableStateFlow(
        Resource.Loading()
    )
    val characters: StateFlow<Resource<List<RelatedTopicModel>>> get() = _characters

    private val _character: MutableStateFlow<Resource<RelatedTopicModel>> = MutableStateFlow(
        Resource.Loading()
    )
    val character: StateFlow<Resource<RelatedTopicModel>> get() = _character

    private val _filteredCharacters: MutableStateFlow<Resource<List<RelatedTopicModel>>> =
        MutableStateFlow(
            Resource.Loading()
        )
    val filteredCharacters: StateFlow<Resource<List<RelatedTopicModel>>> get() = _filteredCharacters

    fun getCharacters(characterType: String) {
        repository.getCharacters(characterType)
            .onEach { _characters.value = Resource.Success(it.data?.relatedTopics) }
            .launchIn(viewModelScope)
    }

    fun getCharacterDetails(charactersList: List<RelatedTopicModel>, position: Int) {
        val selectedChar = charactersList[position]
        _character.value = Resource.Success(selectedChar)
    }

    fun filterCharacters(query: String?) {
        _filteredCharacters.value = Resource.Success(characters.value.data?.filter { character ->
            character.text.lowercase().contains(query.toString().lowercase())
        })
    }
}