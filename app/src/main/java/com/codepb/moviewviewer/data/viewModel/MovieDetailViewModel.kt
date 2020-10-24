package com.codepb.moviewviewer.data.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codepb.moviewviewer.data.MovieViewerRepository
import com.codepb.moviewviewer.data.model.MovieDetails

class MovieDetailViewModel: ViewModel() {

    val mMovieDetail = MutableLiveData<MovieDetails>()

    fun getMovieDetails(){
        MovieViewerRepository.getRepository().getMovieDetail(
            success = {
                mMovieDetail.postValue(it)
            }, errorCallback = { code, message ->
                // ERROR Handling
                Log.w("$code", message)
            })
    }

}