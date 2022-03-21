package com.example.weatherapp.ui.alert.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.AlertLayoutBinding
import com.example.weatherapp.pojo.model.dbentities.AlertEntity

class AlertAdapter(private val list: ArrayList<AlertEntity> = arrayListOf()) :
    RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: ArrayList<AlertEntity>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder =
        AlertViewHolder(AlertLayoutBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = if (list.isNullOrEmpty()) 0 else list.size
    inner class AlertViewHolder(val binding: AlertLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alertEntity: AlertEntity) {
            binding.data = alertEntity
            binding.executePendingBindings()
        }
    }

}