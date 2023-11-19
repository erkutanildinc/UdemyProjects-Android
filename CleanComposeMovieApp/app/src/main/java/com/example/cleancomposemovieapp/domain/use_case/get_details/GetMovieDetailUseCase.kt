package com.example.cleancomposemovieapp.domain.use_case.get_details

import com.example.cleancomposemovieapp.data.remote.dto.toMovieDetail
import com.example.cleancomposemovieapp.domain.model.MovieDetail
import com.example.cleancomposemovieapp.domain.repository.MovieRepository
import com.example.cleancomposemovieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository: MovieRepository){

    fun executeGetMovieDetails(imdbId : String) : Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetail(imdbId = imdbId)
            if(movieDetail.Response.equals("True")){
                emit(Resource.Success(movieDetail.toMovieDetail()))
            }
            else{
                emit(Resource.Error("No Movie Data Found!"))
            }
        }
        catch (e: IOException){
            emit(Resource.Error("No Internet Connection"))
        }
    }
}