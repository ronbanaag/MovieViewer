package com.codepb.moviewviewer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.model.Cinemas
import com.codepb.moviewviewer.data.model.Times
import com.codepb.moviewviewer.ui.viewholder.DataSelectionViewHolder

class TimeItemAdapter (val mContext: Context, val timeList: ArrayList<Times>?, val onSelect: (Times) -> Unit): RecyclerView.Adapter<DataSelectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataSelectionViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_data_selection, parent, false)
        return DataSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataSelectionViewHolder, position: Int) {
        val timesItem = timeList?.get(position)

        holder.tvDataText.text = timesItem?.label
        holder.tvDataSubtext.text = "PHP " + timesItem?.price

        holder.itemView.setOnClickListener {
            onSelect(timesItem!!)
        }
    }

    override fun getItemCount(): Int = timeList?.size ?: 0
}