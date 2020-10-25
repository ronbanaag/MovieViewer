package com.codepb.moviewviewer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.model.Cinemas
import com.codepb.moviewviewer.ui.viewholder.DataSelectionViewHolder
import java.util.ArrayList

class CinemaItemAdapter(val mContext: Context, val cinemaList: ArrayList<Cinemas>?, val onSelect: (Cinemas) -> Unit): RecyclerView.Adapter<DataSelectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataSelectionViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_data_selection, parent, false)
        return DataSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataSelectionViewHolder, position: Int) {
        val cinemaItem = cinemaList?.get(position)

        holder.tvDataText.text = cinemaItem?.label
        holder.tvDataSubtext.visibility = View.GONE

        holder.itemView.setOnClickListener {
            onSelect(cinemaItem!!)
        }
    }

    override fun getItemCount(): Int = cinemaList?.size ?: 0
}