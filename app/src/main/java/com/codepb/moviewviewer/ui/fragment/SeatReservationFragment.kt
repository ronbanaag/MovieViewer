package com.codepb.moviewviewer.ui.fragment

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.model.Cinemas
import com.codepb.moviewviewer.data.model.Times
import com.codepb.moviewviewer.data.viewModel.MovieDetailViewModel
import com.codepb.moviewviewer.ui.activity.MainActivity
import com.codepb.moviewviewer.ui.activity.MainActivity.Companion.VIEW_MODE_SMALL
import com.codepb.moviewviewer.ui.activity.MainActivity.Companion.VIEW_MODE_ZOOMED
import com.codepb.moviewviewer.ui.adapter.SeatmapItemAdapter
import com.codepb.moviewviewer.ui.adapter.SelectedSeatItemAdapter
import kotlinx.android.synthetic.main.fragment_seat_reservation.*

class SeatReservationFragment: BaseFragment() {

    private lateinit var model: MovieDetailViewModel

    private var seatmapItemAdapter: SeatmapItemAdapter? = null
    private val seatMapData: MutableList<String> = ArrayList()

    private var selectedSeatItemAdapter: SelectedSeatItemAdapter? = null
    private val selectedSeatData: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_seat_reservation, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = requireActivity().let { ViewModelProviders.of(it).get(MovieDetailViewModel::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        getData()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if(model.mSeatmap.value != null){
            MainActivity.mainActivityReference?.showLoading()
            createSeatMap(model.mSeatmap.value!!)
        }
    }

    private fun initViews(){
        tv_theater_value?.text = model.mMovieDetail.value?.theater

        selectedSeatItemAdapter = SelectedSeatItemAdapter(requireContext(), selectedSeatData, onSelect = {
            showReservationCancellation(it)
        })
        rv_selected_seats?.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        rv_selected_seats?.adapter = selectedSeatItemAdapter

        if(model.mSelectedSeats.value.isNullOrEmpty()){
            ll_selected_seats?.visibility = View.GONE
        }

        ll_select_date?.setOnClickListener {
            if(model.canSelectDate) {
                val bundle = bundleOf("dates" to model.mViewingDate.value)
                activity?.findNavController(R.id.fl_movie_viewer)?.navigate(
                    R.id.action_seatReservationFragment_to_dateSelectionFragment,
                    bundle
                )
            }
        }

        ll_cinema?.setOnClickListener {
            if(model.canSelectCinema){
                val bundle = bundleOf("cinemas" to model.mCinemasByDate.value)
                activity?.findNavController(R.id.fl_movie_viewer)?.navigate(
                    R.id.action_seatReservationFragment_to_cinemaSelectionFragment,
                    bundle
                )
            } else {
                MainActivity.mainActivityReference?.showConfirmationDialog(requireContext(), resources.getString(R.string.dialog_title_schedule_details), resources.getString(R.string.dialog_message_confirm_select_date))
            }
        }

        ll_select_time?.setOnClickListener {
            if(model.canSelectTime){
                val bundle = bundleOf("times" to model.mTimeByCinema.value)
                activity?.findNavController(R.id.fl_movie_viewer)?.navigate(
                    R.id.timeSelectionFragment,
                    bundle
                )
            } else {
                MainActivity.mainActivityReference?.showConfirmationDialog(requireContext(), resources.getString(R.string.dialog_title_schedule_details), resources.getString(R.string.dialog_message_confirm_select_cinemda))
            }
        }

        bt_zoom_in?.setOnClickListener {
            val currentViewSetting = model.viewMode.value
            if(currentViewSetting == VIEW_MODE_SMALL){
                model.viewMode.postValue(VIEW_MODE_ZOOMED)
            } else {
                model.viewMode.postValue(VIEW_MODE_SMALL)
            }
        }
    }

    private fun initListeners(){
        if(!model.mSelectedSeats.hasObservers()){
            model.mSelectedSeats.observe(viewLifecycleOwner, {
                if(!it.isNullOrEmpty()){
                    selectedSeatData.clear()
                    selectedSeatData.addAll(it)
                    ll_selected_seats?.visibility = View.VISIBLE
                    rv_selected_seats?.adapter?.notifyDataSetChanged()
                } else {
                    ll_selected_seats?.visibility = View.GONE
                    rv_selected_seats?.adapter?.notifyDataSetChanged()
                }

                tv_total_price?.text = model.getTotalPrice()
                if(model.mSeatmap.value != null){
                    MainActivity.mainActivityReference?.showLoading()
                    createSeatMap(model.mSeatmap.value!!)
                }
            })
        }

        if(!model.mSeatmapDownloaded.hasObservers()){
            model.mSeatmapDownloaded.observe(viewLifecycleOwner, {
                createSeatMap(model.mSeatmap.value!!)
            })
        }

        if(!model.mViewingDate.hasObservers()){
            model.mViewingDate.observe(viewLifecycleOwner, {
                model.canSelectDate = true
            })
        }

        if(!model.mSelectedDate.hasObservers()){
            model.mSelectedDate.observe(viewLifecycleOwner, {
                tv_date_value.text = it.label
                getCinemaListFor(model.mSelectedDate.value?.id)
//                model.mSelectedSeats.postValue(null)
            })
        }

        if(!model.mCinemasByDate.hasObservers()){
            model.mCinemasByDate.observe(viewLifecycleOwner, {
                model.canSelectCinema = !it.isNullOrEmpty()
            })
        }

        if(!model.mSelectedCinema.hasObservers()){
            model.mSelectedCinema.observe(viewLifecycleOwner, {
                if(it != null) {
                    tv_cinema_value.text = it.label
                    getTimesListFor(model.mSelectedCinema.value?.id)
                } else {
                    tv_cinema_value.text = resources.getString(R.string.label_none)
                    tv_time_value.text = resources.getString(R.string.label_none)
                }
//                model.mSelectedSeats.postValue(null)
            })
        }

        if(!model.mTimeByCinema.hasObservers()){
            model.mTimeByCinema.observe(viewLifecycleOwner, {
                model.canSelectTime = !it.isNullOrEmpty()
            })
        }

        if(!model.mSelectedTime.hasObservers()){
            model.mSelectedTime.observe(viewLifecycleOwner, {
                if(it != null) {
                    tv_time_value.text = it.label
                } else {
                    tv_time_value.text = resources.getString(R.string.label_none)
                }
//                model.mSelectedSeats.postValue(null)
            })
        }

        if(!model.viewMode.hasObservers()){
            model.viewMode.observe(viewLifecycleOwner, {
                if(model.mSeatmap.value != null){
                    MainActivity.mainActivityReference?.showLoading()
                    createSeatMap(model.mSeatmap.value!!)
                }
                if(it == VIEW_MODE_ZOOMED){
                    bt_zoom_in.setImageResource(R.drawable.ic_baseline_zoom_out)
                } else {
                    bt_zoom_in.setImageResource(R.drawable.ic_baseline_zoom_in)
                }
            })
        }
    }

    private fun getData(){
        model.getSeatmap()
        model.getViewingTime()
        model.viewMode.postValue(VIEW_MODE_SMALL)
        MainActivity.mainActivityReference?.showLoading()
    }

    private fun createSeatMap(rowList: List<List<String>?>) {
        val selectedSeats = model.mSelectedSeats.value
        val availableSeats = model.mAvailableSeats.value
        ll_seat_map.removeAllViews()
        val seatMap: MutableList<List<String>?> = ArrayList()

        seatMap.addAll(rowList)

        seatMap.forEachIndexed { index, list ->
            val rowView = LinearLayout(requireContext())
            rowView.orientation = LinearLayout.HORIZONTAL
            val rowViewParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            rowView.layoutParams = rowViewParams

            val rowLegendLeft = createSeatTextView()
            val rowLegendRight = createSeatTextView()
            rowLegendLeft.text = model.mRowNames.get(index)
            rowLegendRight.text = model.mRowNames.get(index)

            rowView.addView(rowLegendLeft)
            list?.forEach {
                val seatView = createSeatTextView()

                if(it.matches(Regex("([A-Z]|[a-z])"))){
                    seatView.text = it
                } else if (it.matches(Regex("([Aa]\\(30\\))"))) {
                    seatView.text = ""
                } else {
                    if(!selectedSeats.isNullOrEmpty() && it in selectedSeats){
                        val checkImage = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_white)
                        checkImage?.setBounds(0, 0, 0, 0)
                        seatView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.seatingSelected))
                        seatView.setCompoundDrawables(checkImage, null ,null ,null)
                    } else if(availableSeats != null && !availableSeats.seats.isNullOrEmpty() && it in availableSeats.seats){
                        seatView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.seatingAvailable))
                    } else {
                        seatView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.seatingReserved))
                    }
                    seatView.tag = it
                    seatView.setOnClickListener {
                        if(seatView.tag != null){
                            if(model.mSelectedDate.value == null){
                                MainActivity.mainActivityReference?.showConfirmationDialog(requireContext(), resources.getString(R.string.dialog_title_seat_reservation), resources.getString(R.string.dialog_message_confirm_select_schedule_details))
                            } else if(model.mSelectedCinema.value == null){
                                MainActivity.mainActivityReference?.showConfirmationDialog(requireContext(), resources.getString(R.string.dialog_title_seat_reservation), resources.getString(R.string.dialog_message_confirm_select_cinemda))
                            } else if(model.mSelectedTime.value == null){
                                MainActivity.mainActivityReference?.showConfirmationDialog(requireContext(), resources.getString(R.string.dialog_title_seat_reservation), resources.getString(R.string.dialog_message_confirm_select_date))
                            }
                            else {
                                reserveSeat(seatView.tag!!.toString())
                            }
                        }
                    }
                }
                rowView.addView(seatView)
            }
            rowView.addView(rowLegendRight)

            ll_seat_map.addView(rowView)
        }
        MainActivity.mainActivityReference?.hideLoading()
    }

    private fun createSeatTextView(): TextView{
        val seatView = TextView(requireContext())
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        if(model.viewMode.value == VIEW_MODE_SMALL){
            params.height = resources.getDimensionPixelSize(R.dimen.seat_image_height_small)
            params.width = resources.getDimensionPixelSize(R.dimen.seat_image_width_small)
            params.marginEnd = resources.getDimensionPixelSize(R.dimen.seat_image_margin_end_small)
            params.bottomMargin = resources.getDimensionPixelSize(R.dimen.seat_image_margin_bottom_small)
            seatView.textSize = resources.getDimension(R.dimen.font_size_seating_small)
        } else {
            params.height = resources.getDimensionPixelSize(R.dimen.seat_image_height_zoomed)
            params.width = resources.getDimensionPixelSize(R.dimen.seat_image_width_zoomed)
            params.marginEnd = resources.getDimensionPixelSize(R.dimen.seat_image_margin_end_zoomed)
            params.bottomMargin = resources.getDimensionPixelSize(R.dimen.seat_image_margin_bottom_zoomed)
            seatView.textSize = resources.getDimension(R.dimen.font_size_seating_zoomed)
        }
        seatView.layoutParams = params
        return seatView
    }

    private fun reserveSeat(seatTag: String){
        val selectedSeats = model.mSelectedSeats.value
        val availableSeats = model.mAvailableSeats.value
        if(selectedSeats != null && seatTag in selectedSeats){
            showReservationCancellation(seatTag)
        }
        else if(seatTag in availableSeats!!.seats){
            showReservationConfirmation(seatTag)
        } else {
            MainActivity.mainActivityReference?.showConfirmationDialog(requireContext(), resources.getString(R.string.dialog_title_seat_reservation), resources.getString(R.string.dialog_message_seat_not_available))
        }
    }

    private fun showReservationConfirmation(seatTag: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.dialog_title_seat_reservation)
            .setMessage(R.string.dialog_message_confirm_reservation).setCancelable(false)
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                model.addSeatToSelected(seatTag)
            }
            .setNegativeButton(android.R.string.no) { dialog, which -> }
        builder.show()
    }

    private fun showReservationCancellation(seatTag: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.dialog_title_seat_reservation)
            .setMessage(R.string.dialog_message_cancel_reservation).setCancelable(false)
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                model.removeSeatFromSelected(seatTag)
            }
            .setNegativeButton(android.R.string.no) { dialog, which -> }
        builder.show()
    }

    fun getCinemaListFor(selectedDateId: String?){
        if(selectedDateId != null) {
            val cinemaList: MutableList<Cinemas> = ArrayList()
            model.mCinemas.value?.forEach {
                if (it.parent == selectedDateId) {
                    if(it.cinemaList != null) {
                        cinemaList.addAll(it.cinemaList)
                    }
                }
            }
            if (cinemaList.isNullOrEmpty()) {
                model.mSelectedCinema.postValue(null)
                model.mSelectedTime.postValue(null)
                model.mCinemasByDate.postValue(null)
                model.mTimeByCinema.postValue(null)
            } else {
                model.mCinemasByDate.postValue(cinemaList)
            }
        } else {
            Log.e("SELECTION_ERROR", "No Selected Date")
        }
    }

    fun getTimesListFor(selectedCinemaId: String?){
        if(selectedCinemaId != null) {
            val timeList: MutableList<Times> = ArrayList()
            model.mViewingTime.value?.forEach {
                if(it.parent == selectedCinemaId){
                    if(it.times != null){
                        timeList.addAll(it.times)
                    }
                }
            }
            if(timeList.isNullOrEmpty()){
                model.mSelectedTime.postValue(null)
                model.mTimeByCinema.postValue(null)
            } else {
                model.mTimeByCinema.postValue(timeList)
            }
        }
    }
}