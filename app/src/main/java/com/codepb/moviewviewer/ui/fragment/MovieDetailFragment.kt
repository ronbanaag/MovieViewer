package com.codepb.moviewviewer.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.viewModel.MovieDetailViewModel
import com.codepb.moviewviewer.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailFragment: BaseFragment() {

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_movie_details, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieDetailViewModel = requireActivity().let { ViewModelProviders.of(it).get(MovieDetailViewModel::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()

        movieDetailViewModel.getMovieDetails()
        MainActivity.mainActivityReference?.showLoading()
    }

    private fun initViews(){
        b_view_seat_map.setOnClickListener {
            navigateTo(R.id.fl_movie_viewer, R.id.action_movieDetailFragment_to_seatReservationFragment)
        }
    }

    private fun initListeners(){
        if (!movieDetailViewModel.mMovieDetail.hasObservers()) {
            movieDetailViewModel.mMovieDetail.observe(viewLifecycleOwner, Observer {
                tv_movie_name_value.text = it.canonicalTitle
                tv_movie_genre_value.text = it.genre
                tv_movie_advisory_rating_value.text = it.advisoryRating
                // TODO convert to hr. min. format
                tv_movie_duration_value.text = convertRuntimToHrMinFormat(it.runtimeMins)
                tv_movie_release_date_value.text = formatDateValue(it.releaseDate)
                tv_movie_synopsis_value.text = it.synopsis

                Glide.with(this).load(it.poster).into(iv_vertical_poster)
                Glide.with(this).load(it.posterLandscape).into(iv_horizontal_poster)

                MainActivity.mainActivityReference?.hideLoading()
            })
        }
    }

    private fun formatDateValue(date: String?): String{
        val sdfInput = SimpleDateFormat("YYYY-MM-DD", Locale.getDefault())
        val sdfOutput = SimpleDateFormat("MMMM DD, YYYY", Locale.getDefault())
        if (date != null && date.length > 0) {
            try {
                val inputDate = sdfInput.parse(date)
                return sdfOutput.format(inputDate!!)
            } catch (e: ParseException) {
                Log.e("DATE_FORMAT_ERROR", e.message!!)
            }
        }
        return date ?: "No release date"
    }

    private fun convertRuntimToHrMinFormat(totalMinutes: String): String{
        val minuteValue = totalMinutes.toFloat()
        val hourCount = (minuteValue / 60).toInt()
        val minuteCount = (minuteValue % 60).toInt()
        return "$hourCount hrs $minuteCount mins"
    }
}