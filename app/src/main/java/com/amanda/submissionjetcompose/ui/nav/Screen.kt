package com.amanda.submissionjetcompose.ui.nav

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailRecipe : Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
}