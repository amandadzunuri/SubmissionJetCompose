package com.amanda.submissionjetcompose.data

import com.amanda.submissionjetcompose.model.Recipe
import com.amanda.submissionjetcompose.model.RecipesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RecipeRepository {
    private val book = mutableListOf<Recipe>()

    init {
        if (book.isEmpty()) {
            book.addAll(RecipesData.recipes)
//            BooksData.books.forEach {
//                book.addAll()
//            }
        }
    }

    fun getAllBooks(): Flow<List<Recipe>> {
        return flowOf(book)
    }

    fun getBookById(id: Int): Recipe {
        return book.first {
            it.id == id
        }
    }

    fun getFavoriteBook(): Flow<List<Recipe>> {
        return flowOf(book.filter { it.isFavorite })
    }

    fun searchBook(query: String): Flow<List<Recipe>> {
        return flowOf(book.filter { it.name.contains(query, ignoreCase = true) })
    }

    fun updateBook(id: Int, newState: Boolean): Flow<Boolean> {
        val index = book.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val bookData = book[index]
            book[index] = bookData.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: RecipeRepository? = null

        fun getInstance(): RecipeRepository =
            instance ?: synchronized(this) {
                RecipeRepository().apply {
                    instance = this
                }
            }
    }
}