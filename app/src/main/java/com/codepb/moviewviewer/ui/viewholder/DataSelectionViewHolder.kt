package com.codepb.moviewviewer.ui.viewholder

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R
import kotlinx.android.synthetic.main.item_data_selection.view.*

class DataSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvDataText: TextView
    var tvDataSubtext: TextView

    init {
        tvDataText = itemView.findViewById(R.id.tv_data_text)
        tvDataSubtext = itemView.findViewById(R.id.tv_data_subtext)
    }

}