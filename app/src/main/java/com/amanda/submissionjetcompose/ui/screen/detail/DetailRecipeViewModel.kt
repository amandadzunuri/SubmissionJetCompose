package com.amanda.submissionjetcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanda.submissionjetcompose.data.RecipeRepository
import com.amanda.submissionjetcompose.model.Recipe
import com.amanda.submissionjetcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Recipe>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Recipe>> get() = _uiState

    fun getBookById(bookId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBookById(bookId))
        }
    }

    fun updateBook(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateBook(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getBookById(id)
            }
    }
}