package com.amanda.submissionjetcompose.ui.screen.fav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amanda.submissionjetcompose.di.Injection
import com.amanda.submissionjetcompose.model.Recipe
import com.amanda.submissionjetcompose.model.RecipesData
import com.amanda.submissionjetcompose.ui.ViewModelFactory
import com.amanda.submissionjetcompose.ui.common.UiState
import com.amanda.submissionjetcompose.ui.component.RecipeItem
import com.amanda.submissionjetcompose.ui.theme.SubmissionJetComposeTheme

@Composable
fun FavRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: FavRecipeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getFavoriteBook()
            }
            is UiState.Success -> {
                FavoriteContent(
                    recipe = it.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    recipe: List<Recipe>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(400.dp),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(recipe) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToDetail(it.id) }
            ) {
                RecipeItem(
                    image = it.photo,
                    name = it.name,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun FavoriteContentPreview() {
    SubmissionJetComposeTheme {
        FavoriteContent(recipe = RecipesData.recipes, navigateToDetail = {})
    }
}