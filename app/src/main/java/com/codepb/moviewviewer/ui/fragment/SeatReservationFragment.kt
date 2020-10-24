package com.codepb.moviewviewer.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.viewModel.MovieDetailViewModel
import com.codepb.moviewviewer.ui.activity.MainActivity
import com.codepb.moviewviewer.ui.adapter.SeatmapItemAdapter
import kotlinx.android.synthetic.main.fragment_seat_reservation.*

class SeatReservationFragment: BaseFragment() {

    private lateinit var seatReservationModel: MovieDetailViewModel

    private var seatmapItemAdapter: SeatmapItemAdapter? = null
    private val seatMapData: MutableList<String> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_seat_reservation, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        seatReservationModel = requireActivity().let { ViewModelProviders.of(it).get(MovieDetailViewModel::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        getData()
    }

    fun initViews(){
        seatmapItemAdapter = SeatmapItemAdapter(requireContext(), seatMapData, seatReservationModel.mAvailableSeats.value, seatReservationModel.mSelectedSeats.value)
        rv_movie_seating.layoutManager =  GridLayoutManager(requireContext(), 36)
        rv_movie_seating.adapter = seatmapItemAdapter

        ll_cinema.setOnClickListener {

        }

        ll_select_date.setOnClickListener {

        }

        ll_select_time.setOnClickListener {

        }
    }

    fun initListeners(){
        if(!seatReservationModel.mSeatmap.hasObservers()){
            seatReservationModel.mSeatmap.observe(viewLifecycleOwner, {
                MainActivity.mainActivityReference?.hideLoading()
                val filteredData: ArrayList<String> = ArrayList()
                val rowData: ArrayList<String> = ArrayList()

                // Only get data with format A##
                it?.forEach {
                    // Get Rows
                    it?.forEach {
                        filteredData.add(it)
                    }
                }
                Log.w("SEATMAP", filteredData.toString())
                seatMapData.addAll(filteredData)
                rv_movie_seating.adapter?.notifyDataSetChanged()
            })
        }
    }

    fun getData(){
        seatReservationModel.getSeatmap()
        MainActivity.mainActivityReference?.showLoading()
    }
}