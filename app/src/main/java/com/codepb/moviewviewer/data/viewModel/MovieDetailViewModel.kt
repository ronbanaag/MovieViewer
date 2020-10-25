package com.codepb.moviewviewer.data.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codepb.moviewviewer.data.MovieViewerRepository
import com.codepb.moviewviewer.data.model.*

class MovieDetailViewModel: ViewModel() {

    val mMovieDetail = MutableLiveData<MovieDetails>()
    val mCinemas = MutableLiveData<List<ViewingCinemas>>()
    val mViewingDate = MutableLiveData<List<ViewingDate>>()
    val mViewingTime = MutableLiveData<List<ViewingTime>>()
    val mSeatmap = MutableLiveData<List<List<String>?>>()
    val mAvailableSeats = MutableLiveData<AvailableSeats>()
    val mSeatmapDownloaded = MutableLiveData<Boolean>()
    var mRowNames: MutableList<String> = ArrayList()
    var mRowCount = MutableLiveData<Int>()

    val mSelectedSeats = MutableLiveData<List<String>>()
    val mSelectedDate = MutableLiveData<ViewingDate>()
    val mCinemasByDate = MutableLiveData<List<Cinemas>>()
    val mSelectedCinema = MutableLiveData<Cinemas>()
    val mTimeByCinema = MutableLiveData<List<Times>>()
    val mSelectedTime = MutableLiveData<Times>()

    val viewMode = MutableLiveData<Int>()

    var canSelectCinema: Boolean = false
    var canSelectDate: Boolean = false
    var canSelectTime: Boolean = false

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
                mViewingDate.postValue(it.dates)
                mCinemas.postValue(it.cinemas)
                mViewingTime.postValue(it.times)
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
                it.seatmap?.forEach {
                    var rowGuide: String = ""
                    it?.forEach {
                        if(!it.matches(Regex("([Aa]\\(30\\))"))){
                            rowGuide = it.filter {
                                it.isLetter()
                            }
                        }
                    }
                    mRowNames.add(rowGuide)
                }
                mSeatmapDownloaded.postValue(true)
            }, errorCallback = { code, message ->
                Log.w("$code", message)
            }
        )
    }

    fun addSeatToSelected(seatTag: String){
        val selectedSeats: MutableList<String> = ArrayList()
        if(mSelectedSeats.value != null){
            selectedSeats.addAll(mSelectedSeats.value!!)
        }
        selectedSeats.add(seatTag)
        mSelectedSeats.postValue(selectedSeats)
    }

    fun removeSeatFromSelected(seatTag: String){
        val selectedSeats: MutableList<String> = ArrayList()
        if(mSelectedSeats.value != null){
            selectedSeats.addAll(mSelectedSeats.value!!)
        }

        var removeIndex: Int? = null
        selectedSeats.forEachIndexed { index, s ->
            if(s == seatTag){
                removeIndex = index
            }
        }
        if(removeIndex != null){
            selectedSeats.removeAt(removeIndex!!)
        }
        mSelectedSeats.postValue(selectedSeats)
    }

    fun getTotalPrice(): String {
        if(mSelectedTime.value != null && mSelectedSeats.value != null){
            val datePrice = (mSelectedTime.value!!.price).toInt()
            val selectedSeatCount = mSelectedSeats.value!!.size
            val total = datePrice*selectedSeatCount
            return "PHP $total"
        } else {
            return "PHP 0"
        }
    }
}