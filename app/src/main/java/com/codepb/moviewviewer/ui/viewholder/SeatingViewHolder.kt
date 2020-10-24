package com.codepb.moviewviewer.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R

class SeatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvSeatingRow: TextView

    init {
        tvSeatingRow = itemView.findViewById(R.id.tv_seating_row)
    }
}