package com.amanda.submissionjetcompose.di

import com.amanda.submissionjetcompose.data.RecipeRepository

object Injection {
    fun provideRepository() : RecipeRepository{
        return RecipeRepository.getInstance()
    }
}