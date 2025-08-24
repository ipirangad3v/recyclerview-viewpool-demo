package com.tonima.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tonima.recyclerview.R
import com.tonima.recyclerview.utils.PerformanceLogger

class VerticalAdapter(
    private val logger: PerformanceLogger,
    private val sharedPool: RecyclerView.RecycledViewPool? = null
) : RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>() {

    class VerticalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.category_title)
        val horizontalRecyclerView: RecyclerView = view.findViewById(R.id.horizontal_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_list, parent, false)
        return VerticalViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        holder.title.text = "Categoria $position"
        holder.horizontalRecyclerView.adapter = HorizontalAdapter(logger)

        holder.horizontalRecyclerView.setRecycledViewPool(sharedPool)
    }

    override fun getItemCount(): Int = 20
}