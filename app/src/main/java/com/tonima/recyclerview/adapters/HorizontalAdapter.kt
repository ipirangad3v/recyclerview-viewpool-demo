package com.tonima.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tonima.recyclerview.R
import com.tonima.recyclerview.utils.PerformanceLogger

class HorizontalAdapter(private val logger: PerformanceLogger) :
    RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    class HorizontalViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        logger.onViewCreated()

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_list, parent, false)
        return HorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) = Unit

    override fun getItemCount(): Int = 20
}