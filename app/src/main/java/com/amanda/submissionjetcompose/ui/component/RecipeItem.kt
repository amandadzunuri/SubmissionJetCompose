package com.amanda.submissionjetcompose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amanda.submissionjetcompose.R
import com.amanda.submissionjetcompose.ui.theme.SubmissionJetComposeTheme

@Composable
fun RecipeItem(
    image: Int,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(1.dp)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.recipe_cover),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(width = 450.dp, height = 250.dp)
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
        )
        Text(
            text = name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = modifier.padding(top = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BookItemPreview() {
    SubmissionJetComposeTheme {
        RecipeItem(image = R.drawable.spaghetti_bolognese, name = "Spaghetti Bolognese")
    }
}