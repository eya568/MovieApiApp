package com.example.movieapiapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.movieapiapp.common.UIState
import com.example.movieapiapp.data.model.MainData
import com.example.movieapiapp.ui.theme.MovieApiAppTheme
import com.example.movieapiapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieListScreen(viewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MovieListScreen(viewModel: MainViewModel) {
    val state = viewModel.mainItem.collectAsState()

    when (val result = state.value) {
        is UIState.Loading -> LoadingIndicator()
        is UIState.Success -> MovieList(movies = result.data)
        is UIState.Failure -> ErrorMessage(message = result.throwable.message ?: "Something went wrong")
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MovieList(movies: List<MainData>) {
    val checkedStates = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies) { movie ->
            val isChecked = checkedStates[movie.title] ?: false
            MovieItem(
                movie = movie,
                isChecked = isChecked,
                onCheckedChange = { isChecked ->
                    checkedStates[movie.title] = isChecked
                }
            )
        }
    }
}


@Composable
fun MovieItem(
    movie: MainData,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = movie.poster),
            contentDescription = movie.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Year: ${movie.year}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        androidx.compose.material3.Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}



@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
