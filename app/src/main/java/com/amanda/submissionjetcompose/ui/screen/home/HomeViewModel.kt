package com.amanda.submissionjetcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanda.submissionjetcompose.data.RecipeRepository
import com.amanda.submissionjetcompose.model.Recipe
import com.amanda.submissionjetcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Recipe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Recipe>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { books ->
                    _uiState.value = UiState.Success(books)
                }
        }
    }

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchBook(_query.value)
            .catch { _uiState.value = UiState.Error(it.message.toString()) }
            .collect { _uiState.value = UiState.Success(it) }
    }
}