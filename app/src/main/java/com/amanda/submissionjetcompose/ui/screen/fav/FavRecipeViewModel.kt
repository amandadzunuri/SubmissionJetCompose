package com.amanda.submissionjetcompose.ui.screen.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanda.submissionjetcompose.data.RecipeRepository
import com.amanda.submissionjetcompose.model.Recipe
import com.amanda.submissionjetcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavRecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Recipe>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Recipe>>> get() = _uiState

    fun getFavoriteBook() {
        viewModelScope.launch {
            repository.getFavoriteBook()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun updateBook(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateBook(id, !newState)
        getFavoriteBook()
    }
}