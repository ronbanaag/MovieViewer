package com.codepb.moviewviewer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.model.Times
import com.codepb.moviewviewer.data.viewModel.MovieDetailViewModel
import com.codepb.moviewviewer.ui.activity.MainActivity
import com.codepb.moviewviewer.ui.adapter.CinemaItemAdapter
import com.codepb.moviewviewer.ui.adapter.TimeItemAdapter
import kotlinx.android.synthetic.main.fragment_data_selection.*

class TimeSelectionFragment: BaseFragment() {

    private lateinit var model: MovieDetailViewModel

    private var timeSelectionAdapter: TimeItemAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_data_selection, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = requireActivity().let { ViewModelProviders.of(it).get(MovieDetailViewModel::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    fun initView(){
        if(arguments != null){
            val timeList = arguments?.getParcelableArrayList<Times>("times")
            timeSelectionAdapter = TimeItemAdapter(requireContext(), timeList, onSelect = {
                model.mSelectedTime.postValue(it)
                MainActivity.mainActivityReference?.showLoading()
                activity?.findNavController(R.id.fl_movie_viewer)?.popBackStack()
            })
            rv_data_selection?.layoutManager = LinearLayoutManager(requireContext())
            rv_data_selection?.adapter = timeSelectionAdapter
        }
    }
}