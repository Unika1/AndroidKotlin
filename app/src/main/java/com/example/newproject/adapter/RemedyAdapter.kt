package com.example.newproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newproject.R
import com.example.newproject.model.RemedyModel

class RemedyAdapter (
    private var remedyList: List<RemedyModel>,
    private val onUpdateClick: (RemedyModel) -> Unit,
    private val onDeleteClick: (String) -> Unit
): RecyclerView.Adapter<RemedyAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemedyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_remedy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RemedyAdapter.ViewHolder, position: Int) {
        val remedy = remedyList[position]
        holder.tvTitle.text = remedy.title
        holder.tvDescription.text = remedy.description
        holder.tvProcedure.text = remedy.procedure

        holder.btnEdit.setOnClickListener { onUpdateClick(remedy) }
        holder.btnDelete.setOnClickListener { onDeleteClick(remedy.remedyId) }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}