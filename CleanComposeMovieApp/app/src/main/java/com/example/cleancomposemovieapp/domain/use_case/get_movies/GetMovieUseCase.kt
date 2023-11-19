package com.example.cleancomposemovieapp.domain.use_case.get_movies

import com.example.cleancomposemovieapp.data.remote.dto.toMovieList
import com.example.cleancomposemovieapp.domain.model.Movie
import com.example.cleancomposemovieapp.domain.repository.MovieRepository
import com.example.cleancomposemovieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    fun executeGetMovies(search : String) : Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movieList = repository.getMovies(search)
            if(movieList.Response.equals("True")){
                emit(Resource.Success(movieList.toMovieList()))
            }
            else{
                emit(Resource.Error("No Movie Found!"))
            }
        }
        catch (e: IOException){
            emit(Resource.Error("No Internet Connection"))
        }
    }
}