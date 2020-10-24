package com.codepb.moviewviewer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.model.AvailableSeats
import com.codepb.moviewviewer.ui.viewholder.SeatingViewHolder

class SeatmapItemAdapter(val mContext: Context, val seatList: List<String>, val availableSeats: AvailableSeats?, val selectedSeats: List<String>?): RecyclerView.Adapter<SeatingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatingViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_seating, parent, false)
        return SeatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeatingViewHolder, position: Int) {
        val seat = seatList.get(position)
        if(seat.matches(Regex("([A-Z]|[a-z])"))){
            holder.tvSeatingRow.text = seat
        } else if (seat.matches(Regex("([Aa]\\(30\\))"))) {
            holder.tvSeatingRow.text = ""
        } else {
            if(!selectedSeats.isNullOrEmpty() && seat in selectedSeats){
                val checkImage = ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_check_white)
                holder.tvSeatingRow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.seatingSelected))
                holder.tvSeatingRow.setCompoundDrawables(checkImage, null ,null ,null)
            } else if(availableSeats != null && !availableSeats.seats.isNullOrEmpty() && seat in availableSeats.seats){
                holder.tvSeatingRow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.seatingAvailable))
            } else {
                holder.tvSeatingRow.setBackgroundColor(ContextCompat.getColor(mContext, R.color.seatingReserved))
            }
        }
    }

    override fun getItemCount(): Int = seatList.size
}