package com.codepb.moviewviewer.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.codepb.moviewviewer.data.api.ApiManager
import com.codepb.moviewviewer.data.model.MovieDetails

class MovieViewerRepository {
    companion object {
        private val instance: MovieViewerRepository by lazy {
            MovieViewerRepository()
        }

        fun getRepository(): MovieViewerRepository {
            return instance
        }
    }

    fun getMovieDetail(success: (MovieDetails) -> Unit, errorCallback: (Int, String) -> Unit){
        ApiManager.create().getMovieDetails(
            next = {
                if (it != null) {
                    success(it)
                } else {
                    errorCallback(0, "No Value found")
                }
            }, error = {
                if(it.message != null){
                    errorCallback(0, it.message!!)
                } else {
                    errorCallback(-1, "Unknown Error")
                }
            }
        )
    }
}