package com.codepb.moviewviewer.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R

class SelectedSeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvSelectedSeat: TextView

    init {
        tvSelectedSeat = itemView.findViewById(R.id.tv_selected_seat)
    }
}