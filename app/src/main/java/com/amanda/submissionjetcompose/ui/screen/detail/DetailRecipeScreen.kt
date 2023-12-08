package com.amanda.submissionjetcompose.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amanda.submissionjetcompose.R
import com.amanda.submissionjetcompose.di.Injection
import com.amanda.submissionjetcompose.ui.ViewModelFactory
import com.amanda.submissionjetcompose.ui.common.UiState
import com.amanda.submissionjetcompose.ui.theme.SubmissionJetComposeTheme

@Composable
fun DetailScreen(
    bookId: Int,
    viewModel: DetailRecipeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getBookById(bookId = bookId)
            }
            is UiState.Success -> {
                val data = it.data
                DetailContent(
                    image = data.photo,
                    name = data.name,
                    ingredients = data.ingredients,
                    id = data.id,
                    steps = data.steps,
                    cookingTime = data.cookingTime,
                    servings = data.servings,
                    isFavorite = data.isFavorite,
                    onBackClick = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateBook(id, state)
                    }
                )
            }
            is UiState.Error -> {
                Text(text = it.errorMessage)
            }
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    id: Int,
    name: String,
    ingredients: String,
    steps: String,
    cookingTime: String,
    servings: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(top = 70.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            IconButton(
                onClick = onBackClick,
                modifier = modifier

                    .size(40.dp)
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = modifier,
                )
            }

            IconButton(
                onClick = { onFavoriteButtonClicked(id, isFavorite) },
                modifier = modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd),
            ) {
                Icon(
                    imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = if (!isFavorite) stringResource(id = R.string.add_favorite) else stringResource(
                        id = R.string.remove_favorite
                    ),
                    modifier = modifier,
                    tint = if (!isFavorite) Color.Black else Color.Red
                )
            }
        }
        Spacer(modifier = modifier.height(20.dp))

        Text(
            text = name,
            modifier = Modifier
                .padding(bottom = 12.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            ),
        )
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.servings, servings),
                modifier = modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .align(Alignment.Bottom),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = stringResource(id = R.string.cooking_time, cookingTime),
                modifier = modifier
                    .padding(start = 12.dp, end = 12.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Text(
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            ),
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp)
        )
        Text(
            text = ingredients,
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp, bottom = 12.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Left,
                fontSize = 14.sp
            )
        )

        Text(
            text = stringResource(id = R.string.steps),
            style = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp)
        )
        Text(
            text = steps,
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(top = 12.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Left,
                fontSize = 14.sp
            )
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    SubmissionJetComposeTheme() {
        DetailContent(
            image = R.drawable.spaghetti_bolognese,
            name = "Spaghetti Bolognese",
            ingredients = "Spaghetti\n Ground beef\n Onion\n Garlic\n Tomato\n Tomato sauce\n Salt\n Pepper\n Oregano\n Parmesan cheese",
            id = 1,
            steps = "Cook spaghetti according to package instructions.\n Sauté onion and garlic until fragrant.\n Add ground beef and cook until browned.\n Add tomato, tomato sauce, salt, pepper, and oregano.\n Simmer until the sauce thickens.\n Serve over cooked spaghetti and sprinkle with Parmesan cheese.",
            cookingTime = "30 minutes",
            servings = "4",
            isFavorite = true,
            onBackClick = { },
            onFavoriteButtonClicked = { _, _ -> }
        )
    }
}