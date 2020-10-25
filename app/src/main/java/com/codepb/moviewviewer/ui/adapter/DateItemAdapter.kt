package com.codepb.moviewviewer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepb.moviewviewer.R
import com.codepb.moviewviewer.data.model.ViewingDate
import com.codepb.moviewviewer.ui.viewholder.DataSelectionViewHolder
import java.util.ArrayList

class DateItemAdapter(val mContext: Context, val datesList: ArrayList<ViewingDate>?, val onSelect: (ViewingDate) -> Unit): RecyclerView.Adapter<DataSelectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataSelectionViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_data_selection, parent, false)
        return DataSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataSelectionViewHolder, position: Int) {
        val dateItem = datesList?.get(position)

        holder.tvDataText.text = dateItem?.label
        holder.tvDataSubtext.visibility = View.GONE

        holder.itemView.setOnClickListener {
            onSelect(dateItem!!)
        }
    }

    override fun getItemCount(): Int = datesList?.size ?: 0

}