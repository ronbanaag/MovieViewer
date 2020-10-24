package com.codepb.moviewviewer.data.api

import com.codepb.moviewviewer.data.model.MovieDetails
import com.codepb.moviewviewer.data.model.Seatmap
import com.codepb.moviewviewer.data.model.ViewingSchedule
import com.codepb.moviewviewer.data.model.ViewingTime
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("movie.json")
    fun getMovieDetails(): Observable<Response<MovieDetails>>

    @GET("schedule.json")
    fun getViewingTime(): Observable<Response<ViewingSchedule>>

    @GET("seatmap.json")
    fun getSeatMap(): Observable<Response<Seatmap>>

    companion object Factory {

        val baseUrl = "http://ec2-52-76-75-52.ap-southeast-1.compute.amazonaws.com/"

        fun create(): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .apply {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    })
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}