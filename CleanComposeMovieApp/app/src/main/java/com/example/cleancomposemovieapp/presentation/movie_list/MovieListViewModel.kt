package com.example.cleancomposemovieapp.presentation.movie_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleancomposemovieapp.domain.use_case.get_details.GetMovieDetailUseCase
import com.example.cleancomposemovieapp.domain.use_case.get_movies.GetMovieUseCase
import com.example.cleancomposemovieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMovieUseCase: GetMovieUseCase): ViewModel() {

    private val _state = mutableStateOf<MovieListState>(MovieListState())
    val state : State<MovieListState> = _state

    private var job : Job? = null

    init {
        getMovies(_state.value.search)
    }

    private fun getMovies(search : String){
        job?.cancel()

        job = getMovieUseCase.executeGetMovies(search = search).onEach {
            when(it){
                is Resource.Success -> {
                    _state.value = MovieListState(movies = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MovieListState(error = "Error!")
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event : MovieListEvent){
        when(event){
            is MovieListEvent.Search ->{
                getMovies(event.searchString)
            }
        }
    }

}