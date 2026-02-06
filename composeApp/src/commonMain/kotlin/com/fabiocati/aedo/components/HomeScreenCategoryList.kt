package com.fabiocati.aedo.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.models.Movie

@Composable
fun HomeScreenCategoryList(
    categoryName: String,
    movies: List<Movie>,
    onMovieClicked: (movie: Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.height(250.dp).padding(bottom = 12.dp)) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.padding(16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items = movies) { movie ->
                AsyncImage(
                    model = movie.posterPath,
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(170.dp)
                        .width(120.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable(
                            onClick = {
                                onMovieClicked(movie)
                            }
                        )
                )
            }
        }
    }
}