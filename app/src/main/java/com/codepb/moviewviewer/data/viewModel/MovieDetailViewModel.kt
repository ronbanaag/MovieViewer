package com.codepb.moviewviewer.data.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codepb.moviewviewer.data.MovieViewerRepository
import com.codepb.moviewviewer.data.model.*

class MovieDetailViewModel: ViewModel() {

    val mMovieDetail = MutableLiveData<MovieDetails>()
    val mCinemas = MutableLiveData<Cinemas>()
    val mViewingDate = MutableLiveData<ViewingDate>()
    val mViewingTime = MutableLiveData<ViewingTime>()
    val mSeatmap = MutableLiveData<List<List<String>?>?>()
    val mAvailableSeats = MutableLiveData<AvailableSeats>()
    var mRowNames: List<String> = ArrayList()
    var mRowCount = MutableLiveData<Int>()

    val mSelectedSeats = MutableLiveData<List<String>>()
    val mSelectedCinema = MutableLiveData<Cinemas>()
    val mSelectedDate = MutableLiveData<ViewingDate>()
    val mSelectedTime = MutableLiveData<ViewingTime>()

    fun getMovieDetails(){
        MovieViewerRepository.getRepository().getMovieDetail(
            success = {
                mMovieDetail.postValue(it)
            }, errorCallback = { code, message ->
                // TODO ERROR Handling
                Log.w("$code", message)
            })
    }

    fun getViewingTime(){
        MovieViewerRepository.getRepository().getViewingTime(
            success = {
                if(it.cinemas != null){
                    mCinemas.postValue(it.cinemas)
                }
                if(it.dates != null){
                    mViewingDate.postValue(it.dates)
                }
                if(it.times != null){
                    mViewingTime.postValue(it.times)
                }
            }, errorCallback = { code, message ->
                Log.w("$code", message)
            }
        )
    }

    fun getSeatmap(){
        MovieViewerRepository.getRepository().getSeatmap(
            success = {
                if(it.seatmap != null){
                    mSeatmap.postValue(it.seatmap)
                    mRowCount.postValue(it.seatmap.size)
                }
                if(it.available != null){
                    mAvailableSeats.postValue(it.available)
                }
            }, errorCallback = { code, message ->
                Log.w("$code", message)
            }
        )
    }
}