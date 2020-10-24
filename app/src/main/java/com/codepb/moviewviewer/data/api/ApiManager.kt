package com.codepb.moviewviewer.data.api

import com.codepb.moviewviewer.data.model.MovieDetails
import com.codepb.moviewviewer.data.model.Seatmap
import com.codepb.moviewviewer.data.model.ViewingSchedule
import com.codepb.moviewviewer.data.model.ViewingTime
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ApiManager {

    var mCompositeDisposable = CompositeDisposable()
    var apiService = ApiService.create()

    companion object Factory {
        fun create(): ApiManager = ApiManager()
    }

    fun dispose() {
        mCompositeDisposable.dispose()
    }

    fun getMovieDetails(next: (data: MovieDetails?) -> Unit, error: (e: Throwable) -> Unit){

        val call = apiService.getMovieDetails()
        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<MovieDetails>>{
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    mCompositeDisposable.add(d)
                }

                override fun onNext(it: Response<MovieDetails>) {
                    if(it.code() == 200) {
                        next(it.body())
                    }
                }

                override fun onError(e: Throwable) {
                    error(e)
                }
            })
    }

    fun getViewingTime(next: (data: ViewingSchedule?) -> Unit, error: (e: Throwable) -> Unit){
        val call = apiService.getViewingTime()
        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<ViewingSchedule>>{
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    mCompositeDisposable.add(d)
                }

                override fun onNext(it: Response<ViewingSchedule>) {
                    if(it.code() == 200) {
                        if(it.body() != null) {
                            next(it.body())
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    error(e)
                }
            })
    }

    fun getSeatmap(next: (data: Seatmap?) -> Unit, error: (e: Throwable) -> Unit){
        val call = apiService.getSeatMap()
        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<Seatmap>>{
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    mCompositeDisposable.add(d)
                }

                override fun onNext(it: Response<Seatmap>) {
                    if(it.code() == 200) {
                        if(it.body() != null) {
                            next(it.body()!!)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    error(e)
                }
            })
    }
}