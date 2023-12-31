package com.example.cleancomposemovieapp.presentation.movie_list.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cleancomposemovieapp.presentation.movie_list.MovieListEvent
import com.example.cleancomposemovieapp.presentation.movie_list.MovieListViewModel

@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel : MovieListViewModel = hiltViewModel()
){
    val state = viewModel.state.value

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)){
        Column {
            MovieSearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), hint = "Martian", onSearch = {
                viewModel.onEvent(MovieListEvent.Search(it))
            })
            
            LazyColumn(modifier= Modifier.fillMaxSize()){
                items(state.movies){
                    MovieListRow(movie = it, onItemClick = {
                        navController.navigate("movie_detail_screen"+"/${it.imdbID}")
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchBar(
    modifier: Modifier,
    hint : String = "",
    onSearch : (String) -> Unit = {}
){

    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplay by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier){
        TextField(value = text, onValueChange = {
            text = it
        },
            keyboardActions = KeyboardActions(onDone = {onSearch(text)}),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(
                    Color.White,
                    CircleShape
                )
                .padding(horizontal = 20.dp)
                .onFocusChanged {
                    isHintDisplay = it.isFocused != true && text.isEmpty()
                }
        )

        if (isHintDisplay){
            Text(text = hint,color= Color.LightGray,modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }
    }
}

