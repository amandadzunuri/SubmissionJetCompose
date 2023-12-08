package com.amanda.submissionjetcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amanda.submissionjetcompose.data.RecipeRepository
import com.amanda.submissionjetcompose.ui.screen.detail.DetailRecipeViewModel
import com.amanda.submissionjetcompose.ui.screen.fav.FavRecipeViewModel
import com.amanda.submissionjetcompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailRecipeViewModel::class.java)) {
            return DetailRecipeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavRecipeViewModel::class.java)) {
            return FavRecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}