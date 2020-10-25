package com.codepb.moviewviewer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.model.AvailableSeats
import com.codepb.moviewviewer.ui.viewholder.SeatingViewHolder
import com.codepb.moviewviewer.ui.viewholder.SelectedSeatViewHolder

class SelectedSeatItemAdapter (val mContext: Context, val selectedSeats: List<String>?, val onSelect: (String) -> Unit): RecyclerView.Adapter<SelectedSeatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSeatViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_seat, parent, false)
        return SelectedSeatViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedSeatViewHolder, position: Int) {
        val selectedSeat = selectedSeats?.get(position)
        if(selectedSeat != null){
            holder.tvSelectedSeat.text = selectedSeat
            holder.itemView.setOnClickListener {
                onSelect(selectedSeat)
            }
        }
    }

    override fun getItemCount(): Int = selectedSeats?.size ?: 0

}